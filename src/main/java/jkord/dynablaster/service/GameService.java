package jkord.dynablaster.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import jkord.core.domain.User;
import jkord.core.service.UserService;
import jkord.core.service.util.RandomUtil;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.domain.*;
import jkord.dynablaster.domain.obj.BotObject;
import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.Direction;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.entity.LobbyUser;
import jkord.dynablaster.repository.LobbyRepository;
import jkord.dynablaster.web.MsgRoute;
import jkord.dynablaster.web.dto.GameDTO;
import jkord.dynablaster.web.dto.MapPositionObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.*;

@Service
public class GameService {

    private static final ConcurrentMap<String, IGame> games = new ConcurrentHashMap<>();
    private static final Map<String, String> usersKeys = new HashMap<>();
    private static ExecutorService executorService;

    @Inject private UserService userService;
    @Inject private LobbyService lobbyService;
    @Inject private LobbyRepository rlobby;

    @Inject protected MessagingService sMessaging;

    @PostConstruct
    private void init() {
        PlayerObject.SMessaging = sMessaging;
        BotObject.SMessaging = sMessaging;

        executorService = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
                .setNameFormat("game-%d")
                .setDaemon(true)
                .build()
        );
    }

    public IGame createGame(GameType type, Long lobbyId) {
        String key = RandomUtil.generateKeyGame();

        IGame game;
        switch (type) {
            case SINGLE: {
                game = new SingleGame(key);
                User user = userService.getUserWithAuthorities();
                ((SingleGame) game).start(user);
                addUserKey(user.getLogin(), key);
            } break;
            case MULTI: {
                game = new MultiGame(key);
                Lobby lobby = rlobby.findOneById(lobbyId);
                ((MultiGame) game).start(lobby);

                lobby.getUsers().forEach(lobbyUser -> addUserKey(lobbyUser.getUser().getLogin(), key));
                sMessaging.send(String.format(MsgRoute.GAME_START, lobby.getId()), new GameDTO(game));

                lobby.setActive(true);
                rlobby.save(lobby);
            } break;
            case BOTS: {
                game = new SingleWithBotsGame(key);
                User user = userService.getUserWithAuthorities();
                ((SingleWithBotsGame) game).start(user);
                addUserKey(user.getLogin(), key);
            } break;
            default: {
                throw new CustomParameterizedException("Game type is not supported");
            }
        }
        addGame(game);

        return game;
    }


    public void addGame(IGame game) {
        executorService.execute(game);
        games.put(game.getKey(), game);
    }

    public void endGame(IGame game) {
        game.end();
        removeGame(game.getKey());
        switch (game.getType()) {
            case SINGLE: case BOTS: {
                removeUserKey(((SingleGame) game).getUser().getLogin());
            } break;
            case MULTI: {
                Lobby lobby = ((MultiGame) game).getLobby();
                lobby.getUsers().forEach(lobbyUser -> removeUserKey(lobbyUser.getUser().getLogin()));
            } break;
            default: {
                throw new CustomParameterizedException("Game type is not supported");
            }
        }
    }

    public void removeGame(String key) {
        games.remove(key);
    }

    public void addUserKey(String name, String key) {
        usersKeys.put(name, key);
    }

    public String getUserKey(String name) {
        return usersKeys.get(name);
    }

    public void removeUserKey(String name) {
        usersKeys.remove(name);
    }

    public IGame getGame(String key) {
        return games.get(key);
    }

    public IGame getGame(HttpSession session) {
        String key = String.valueOf(session.getAttribute(IGame.KEY_NAME));
        return (key.equals("null"))? null : getGame(key);
    }

    public IGame getGameByUserName(String userName) {
        return getGame(getUserKey(userName));
    }

    public User getUserInGamaByName(IGame game, String name) {
        if (game instanceof SingleGame) {
            return ((SingleGame) game).getUser();
        }

        if (game instanceof MultiGame) {
            for (LobbyUser lobbyUser : ((MultiGame) game).getLobby().getUsers()) {
                if (lobbyUser.getUser().getLogin().equals(name))
                    return lobbyUser.getUser();
            }
        }

        return null;
    }

    public void movePlayer(User user, IGame game, String direction) {
        PlayerObject player = game.getCurrentPlayer(user.getId());
        player.move(Direction.valueOf(direction.toUpperCase()));
        sMessaging.send(String.format(MsgRoute.PLAYER_MOVE, player.getId()), player.getPosition());
    }

    public void bombBurst(User user, IGame game, Position position) {
        PlayerObject player = game.getCurrentPlayer(user.getId());
        player.putBomb(position);

        List<MapPositionObject> destroyObjects = new LinkedList<>();
        game.getMap().getDestroyObjects().forEach((poz, mapObject) ->
            destroyObjects.add(new MapPositionObject(poz, mapObject))
        );
        sMessaging.send(String.format(MsgRoute.PLAYER_BOMB, player.getId()), destroyObjects);
    }

    /*public ArrayList<Position> moveBot(IGame game, int id) {
        BotObject bot = game.getMap().getBots().get(id);
        if (bot == null)
            return null;

        GameMap map = game.getMap();
        if (bot.getPathToGo() != null) {
            Position oldPoz = bot.getPathToGo().get(bot.getPathToGo().size() - 1);
            bot.move(oldPoz.getX(), oldPoz.getY());
        } else {
            map.setFastObjToMap(
                new MapObject(MapObjectType.FREE),
                bot.getPosition().getX(),
                bot.getPosition().getY()
            );
        }

        Position pos = GameMap.getRandPosition();
        for (int i = 0; i < 3; i++) {
            if (map.isFreePosition(pos.getX(), pos.getY()))
                break;
            pos = GameMap.getRandPosition();
        }
        bot.movePath(pos.getX(), pos.getY());

        return bot.getPathToGo();
    }*/
}
