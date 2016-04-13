package jkord.dynablaster.service;

import jkord.core.domain.User;
import jkord.core.service.UserService;
import jkord.core.web.rest.errors.CustomParameterizedException;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.entity.LobbyUser;
import jkord.dynablaster.repository.LobbyRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class LobbyService {

    @Inject
    private UserService userService;

    @Inject
    private LobbyRepository lobbyRepository;

    public void create(Lobby lobby) {
        User owner = userService.getUserWithAuthorities();
        lobby.setOwner(owner);
        lobby.addUser(owner);
        lobbyRepository.save(lobby);
    }

    public Lobby findOneActiveById(Long lobbyId) {
        Lobby lobby = lobbyRepository.findOneById(lobbyId);
        if (! lobby.isActive())
            return lobby;

        throw new CustomParameterizedException("Game started");
    }

    public void addUserToLobby(Long lobbyId) {
        Lobby lobby = findOneActiveById(lobbyId);

        if (lobby.getUsers().size() == 4) {
            throw new CustomParameterizedException("Only 4 players can play in game");
        }

        User user = userService.getUserWithAuthorities();

        for (LobbyUser lobbyUser: lobby.getUsers()) {
            if (lobbyUser.getId().equals(user.getId())) {
                return;
            }
        }

        lobby.addUser(user);
        lobbyRepository.save(lobby);
    }

    public void removeUserFromLobby(Long lobbyId) {
        Lobby lobby = findOneActiveById(lobbyId);
        User user = userService.getUserWithAuthorities();
        lobby.removeUser(user);
        lobbyRepository.save(lobby);
    }
}
