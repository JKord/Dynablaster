package jkord.dynablaster.web;

import jkord.core.service.UserService;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.piece.Direction;
import jkord.dynablaster.service.GameService;
import jkord.dynablaster.domain.piece.Position;;
import jkord.dynablaster.web.dto.DirectionMsg;
import jkord.core.domain.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import javax.inject.Inject;
import java.security.Principal;

@Controller
public class GameMsgController {

    @Inject
    private GameService gameService;

    @MessageMapping("/game/start")
    public void start(SimpMessageHeaderAccessor headerAccessor) throws Exception {
        headerAccessor
            .getSessionAttributes()
            .put(IGame.KEY_NAME, gameService.getUserKey(headerAccessor.getUser().getName()));
    }

    @MessageMapping("/player/move")
    @SendTo("/game/hero/move")
    public Position move(SimpMessageHeaderAccessor headerAccessor, DirectionMsg msg) throws Exception {
        String key = (String) headerAccessor.getSessionAttributes().get(IGame.KEY_NAME);
        IGame game = gameService.getGame(key);
        User user = gameService.getUserInGamaByName(game, headerAccessor.getUser().getName());
        if (game == null) {
            throw new CustomParameterizedException("Game not started");
        }

        return gameService.movePlayer(user, game, Direction.valueOf(msg.getDirection().toUpperCase()));
    }
}
