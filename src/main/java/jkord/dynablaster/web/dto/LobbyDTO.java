package jkord.dynablaster.web.dto;

import jkord.core.domain.User;
import jkord.dynablaster.entity.Lobby;
import jkord.dynablaster.entity.LobbyUser;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class LobbyDTO extends LobbyInfoDTO implements Serializable {

    private boolean isActive = false;
    private ZonedDateTime createdAt;
    private List<LobbyUserDTO> users;
    private LobbyUserDTO currentUser;
    private boolean isOwner;

    public LobbyDTO(Lobby lobby, User user) {
        super(lobby);

        createdAt = lobby.getCreatedAt();
        isActive = lobby.isActive();
        this.isOwner = lobby.getOwner().equals(user);

        users = new ArrayList<>(4);
        lobby.getUsers().forEach(lobbyUser ->  {
            LobbyUserDTO lobbyUserDTO = new LobbyUserDTO(lobbyUser);
            if (lobbyUser.getUser().equals(user)) {
                currentUser = lobbyUserDTO;
            }
            users.add(lobbyUserDTO);
        });
    }

    public boolean isActive() {
        return isActive;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public List<LobbyUserDTO> getUsers() {
        return users;
    }

    public LobbyUserDTO getCurrentUser() {
        return currentUser;
    }

    public boolean isOwner() {
        return isOwner;
    }
}
