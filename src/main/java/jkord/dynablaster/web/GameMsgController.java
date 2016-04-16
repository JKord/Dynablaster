package jkord.dynablaster.web;

import jkord.core.domain.User;
import jkord.core.service.UserService;
import jkord.dynablaster.domain.IGame;
import jkord.dynablaster.domain.piece.Position;
import jkord.dynablaster.service.LobbyService;
import jkord.dynablaster.web.dto.DirectionMsg;
import jkord.dynablaster.web.dto.LobbyUserStatus;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

@Controller
public class GameMsgController extends BaseGameController {

    @Inject private LobbyService sLobby;
    @Inject private UserService sUser;

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

    @MessageMapping("/game/lobby/user/status")
    public void lobbyUpdate(SimpMessageHeaderAccessor headerAccessor, LobbyUserStatus lobbyUserStatus) {
        User user = sUser.getUserWithAuthoritiesByLogin(headerAccessor.getUser().getName()).get();
        sLobby.lobbyChangeUserStatus(lobbyUserStatus.getLobbyId(), user, lobbyUserStatus.isActive());
    }
}
