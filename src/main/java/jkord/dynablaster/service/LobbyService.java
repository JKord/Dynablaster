package jkord.dynablaster.service;

import jkord.core.service.UserService;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.repository.LobbyRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class LobbyService {

    @Inject
    private UserService userService;

    @Inject
    private LobbyRepository lobbyRepository;

    public void create(Lobby lobby) {
        lobby.addUser(userService.getUserWithAuthorities());
        lobbyRepository.save(lobby);
        //lobbyRepository.flush();
    }

    public Lobby findOneActiveById(Long lobbyId) {
        Lobby lobby = lobbyRepository.findOne(lobbyId);
        if (lobby.isActive())
            return lobby;

        throw new CustomParameterizedException("Game started");
    }
}
