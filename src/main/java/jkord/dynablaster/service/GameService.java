package jkord.dynablaster.service;

import jkord.core.domain.User;
import jkord.core.service.UserService;
import jkord.core.service.util.RandomUtil;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.domain.*;
import jkord.dynablaster.domain.obj.MapObject;
import jkord.dynablaster.domain.obj.PlayerObject;
import jkord.dynablaster.domain.piece.Direction;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.domain.piece.MapObjectType;
import jkord.dynablaster.domain.piece.Position;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
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
        switch (type) { // TODO
            case SINGLE: {
                game = new SingleGame(key);
                User user = userService.getUserWithAuthorities();
                ((SingleGame) game).start(user);
                addUserKey(user.getLogin(), key);
            } break;
            case MULTI: {
                game = new MultiGame(key);
            } break;
            case BOTS: {
                game = new SingleWithBotsGame(key);
            } break;
            default: {
                throw new CustomParameterizedException("Game type is not supported");
            }
        }
        addGame(game);

        return game;
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

    public void removeUserKey(Long id) {
        usersKeys.remove(id);
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

    public Position movePlayer(User user, IGame game, Direction direction) {
        PlayerObject player = game.getCurrentPlayer(user.getId());

        int x = player.getPosition().getX(), y = player.getPosition().getY();
        switch (direction) {
            case UP: y--; break;
            case DOWN: y++; break;
            case LEFT: x--; break;
            case RIGHT: x++; break;
        }
        if (x >= 0 && y >= 0 && x < GameMap.HORIZONTAL_SIZE && y < GameMap.VERTICAL_SIZE) {
            player.move(x, y);
        }
        return player.getPosition();
    }
}
