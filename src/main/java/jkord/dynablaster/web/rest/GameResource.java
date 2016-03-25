package jkord.dynablaster.web.rest;

import jkord.core.security.AuthoritiesConstants;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.domain.Game;
import jkord.dynablaster.domain.MapObject;
import jkord.dynablaster.domain.piece.GameType;
import jkord.dynablaster.service.GameService;
import jkord.dynablaster.web.dto.GameDTO;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@RestController
@Secured(AuthoritiesConstants.USER)
@RequestMapping("/api/game")
public class GameResource {

    private static final String GAME_KEY = "gameKey";

    @Inject
    private GameService gameService;

    @Inject
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(value = "/start/{type}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public GameDTO start(HttpSession session, @PathVariable String type) {

        switch (GameType.valueOf(type.toUpperCase())) { // TODO
            case SINGLE: {

            } break;
            case MULTI: {

            } break;
            case BOTS: {

            } break;
            default: {
                throw new CustomParameterizedException("Game type is not supported");
            }
        }

        Game game;
        String key = String.valueOf(session.getAttribute(GAME_KEY));
        if (key.equals("null") || gameService.getGameByKey(key) == null) {
            game = gameService.createGame();
            session.setAttribute(GAME_KEY, game.getKey());
            game.start();
        } else {
            game = gameService.getGameByKey(key);
        }

        return new GameDTO(game);
    }

    @RequestMapping(value = "/test-send-msg", method = RequestMethod.GET)
    public String testSendMsg() {

        //simpMessagingTemplate.convertAndSend("/queue/chats-" + "mycustomidentifier", "[" + getTimestamp() + "]:" + message.getMessage());

        simpMessagingTemplate.convertAndSend("move", "test");

        return "ok";
    }
}
