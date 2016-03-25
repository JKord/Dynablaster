package jkord.dynablaster.web;

import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.piece.Direction;
import jkord.dynablaster.service.GameService;
import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.web.dto.DirectionMsg;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class GameMsgController {

    @Inject
    private GameService gameService;

    @MessageMapping("/player/move")
    @SendTo("/game/hero/move")
    public Position move(SimpMessageHeaderAccessor headerAccessor, DirectionMsg msg) throws Exception {

        Map<String, Object> attrs = headerAccessor.getSessionAttributes();

        /*IGame game = gameService.getGameBySession(session);
        if (game == null) {
            throw new CustomParameterizedException("Game not started");
        }

        return gameService.movePlayer(game, Direction.valueOf(msg.getDirection().toUpperCase()));*/

        return new Position(0, 1);
    }
}
