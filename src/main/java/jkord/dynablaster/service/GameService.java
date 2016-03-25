package jkord.dynablaster.service;

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

    @Inject
    private UserService userService;

    public IGame createGame(GameType type) {
        String key = RandomUtil.generateKeyGame();

        IGame game;
        switch (type) { // TODO
            case SINGLE: {
                game = new SingleGame(key);
                ((SingleGame) game).start(userService.getUserWithAuthorities());
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

    public IGame getGameByKey(String key) {
        return games.get(key);
    }

    public IGame getGameBySession(HttpSession session) {
        String key = String.valueOf(session.getAttribute(IGame.KEY_NAME));
        return (key.equals("null"))? null : getGameByKey(key);
    }

    public void removeGame(String key) {
        games.remove(key);
    }

    public Position movePlayer(IGame game, Direction direction) {
        PlayerObject player = game.getCurrentPlayer(userService.getUserWithAuthorities().getId());

        int x = player.getPosition().getX(), y = player.getPosition().getY();
        switch (direction) {
            case UP: x--; break;
            case DOWN: x++; break;
            case LEFT: y--; break;
            case RIGHT: y++; break;
        }
        if (x > -1 && y > -1 && x < GameMap.VERTICAL_SIZE && y < GameMap.HORIZONTAL_SIZE) {
            player.move(x, y);
        }
        return player.getPosition();
    }
}
