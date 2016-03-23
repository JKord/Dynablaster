package jkord.dynablaster.web.rest;

import jkord.core.security.AuthoritiesConstants;
import jkord.core.service.UserService;
import jkord.dynablaster.domain.MapObject;
import jkord.dynablaster.service.GameService;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;

@RestController
@Secured(AuthoritiesConstants.USER)
@RequestMapping("/api/game")
public class GameResource {

    @Inject
    private GameService gameService;

    @Inject
    private UserService userService;

    @Inject
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MapObject[][] map() {
        return gameService.createMap().getMap();
    }

    @RequestMapping(value = "/test-send-msg", method = RequestMethod.GET)
    public String testSendMsg() {

        //simpMessagingTemplate.convertAndSend("/queue/chats-" + "mycustomidentifier", "[" + getTimestamp() + "]:" + message.getMessage());

        simpMessagingTemplate.convertAndSend("move", "test");

        return "ok";
    }
}
