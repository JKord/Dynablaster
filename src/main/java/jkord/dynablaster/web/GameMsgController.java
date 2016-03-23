package jkord.dynablaster.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameMsgController {

    @MessageMapping("/game-msg")
    @SendTo("/move")
    public String move(Object message) throws Exception {
        Thread.sleep(3000);

        return "Hello, " + String.valueOf((byte) message) + "!";
    }
}
