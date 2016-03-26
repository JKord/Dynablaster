package jkord.dynablaster.web;

import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.web.dto.DirectionMsg;
import jkord.core.domain.User;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameMsgController extends BaseGameController{

    @MessageMapping("/game/start")
    public void start(SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor
            .getSessionAttributes()
            .put(IGame.KEY_NAME, gameService.getUserKey(headerAccessor.getUser().getName()));
    }

    @MessageMapping("/player/move")
    @SendTo("/game/hero/move")
    public Position movePlayer(SimpMessageHeaderAccessor headerAccessor, DirectionMsg msg) {
        return gameService.movePlayer(
            getUser(headerAccessor),
            getGame(headerAccessor),
            msg.getDirection()
        );
    }

    @MessageMapping("/bot/move")
    @SendTo("/game/bot/move")
    public void moveBot(SimpMessageHeaderAccessor headerAccessor) {
        IGame game = getGame(headerAccessor);
        User user = getUser(headerAccessor);



    }
}
