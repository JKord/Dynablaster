package jkord.dynablaster.web;

import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.web.dto.DirectionMsg;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller // FIXME: Mapping on one game (Problem SendTo - all subscriptions) - key
public class GameMsgController extends BaseGameController {

    @MessageMapping("/game/start")
    public void start(SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor
            .getSessionAttributes()
            .put(IGame.KEY_NAME, gameService.getUserKey(headerAccessor.getUser().getName()));
    }

    @MessageMapping("/player/move")
    public void movePlayer(SimpMessageHeaderAccessor headerAccessor, DirectionMsg msg) {
        gameService.movePlayer(getUser(headerAccessor), getGame(headerAccessor), msg.getDirection());
    }

    @MessageMapping("/player/bomb")
    public void bombBurst(SimpMessageHeaderAccessor headerAccessor, Position position) {
        gameService.bombBurst(getUser(headerAccessor), getGame(headerAccessor), position);
    }
}
