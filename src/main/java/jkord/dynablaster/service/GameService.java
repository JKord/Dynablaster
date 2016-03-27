package jkord.dynablaster.service;

import jkord.core.domain.User;
import jkord.core.service.UserService;
import jkord.core.service.util.RandomUtil;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.domain.*;
import jkord.dynablaster.domain.obj.BotObject;
import jkord.dynablaster.domain.obj.MapObject;
import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.Direction;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {

    private static final Map<String, IGame> games = new HashMap<>();
    private static final Map<String, String> usersKeys = new HashMap<>();

    @Inject
    private UserService userService;

    public IGame createGame(GameType type) {
        String key = RandomUtil.generateKeyGame();

        IGame game;
        switch (type) {
            case SINGLE: {
                game = new SingleGame(key);
                User user = userService.getUserWithAuthorities();
                ((SingleGame) game).start(user);
                addUserKey(user.getLogin(), key);
            } break;
            case MULTI: { // TODO
                game = new MultiGame(key);
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

    public void endGame(IGame game) {
        game.end();
        removeGame(game.getKey());
        switch (game.getType()) {
            case SINGLE: case BOTS: {
                removeUserKey(((SingleGame) game).getUser().getLogin());
            } break;
            case MULTI: { // TODO

            } break;
            default: {
                throw new CustomParameterizedException("Game type is not supported");
            }
        }
    }

    public void addGame(IGame game) {
        games.put(game.getKey(), game);
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

        return null;
    }

    public Position movePlayer(User user, IGame game, String direction) {
        PlayerObject player = game.getCurrentPlayer(user.getId());
        player.move(Direction.valueOf(direction.toUpperCase()));

        return player.getPosition();
    }

    public ArrayList<Position> moveBot(IGame game, int id) {
        BotObject bot = game.getMap().getBots().get(id);
        if (bot == null)
            return null;

        GameMap map = game.getMap();
        if (bot.getPathToGo() != null) {
            Position oldPoz = bot.getPathToGo().get(bot.getPathToGo().size() - 1);
            bot.move(oldPoz.getX(), oldPoz.getY());
        } else {
            map.setObjToMapWithoutCheck(
                new MapObject(MapObjectType.FREE),
                bot.getPosition().getX(),
                bot.getPosition().getY()
            );
        }

        Position pos = GameMap.getRandPosition();
        for (int i = 0; i < 10; i++) {
            if (map.isFreePosition(pos.getX(), pos.getY()))
                break;
            pos = GameMap.getRandPosition();
        }
        bot.movePath(pos.getX(), pos.getY());

        return bot.getPathToGo();
    }

    public void bombBurst(User user, IGame game, Position position) {
        PlayerObject player = game.getCurrentPlayer(user.getId());
        player.putBomb(position);
    }
}
