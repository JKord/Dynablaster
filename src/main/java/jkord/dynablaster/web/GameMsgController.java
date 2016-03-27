package jkord.dynablaster.web;

import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.web.dto.BotMsg;
import jkord.dynablaster.web.dto.DirectionMsg;
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
    public BotMsg moveBot(SimpMessageHeaderAccessor headerAccessor, BotMsg botMsg) {
        botMsg.setPath(gameService.moveBot(getGame(headerAccessor), botMsg.getId()));
        return botMsg;
    }
}
