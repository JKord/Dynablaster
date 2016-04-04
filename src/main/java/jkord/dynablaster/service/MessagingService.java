package jkord.dynablaster.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MessagingService {

    @Inject
    private SimpMessagingTemplate simpMessagingTemplate;

    // Maybe add key game ??
    public void send(String destination, Object payload) {
        simpMessagingTemplate.convertAndSend(destination, payload);
    }
}
