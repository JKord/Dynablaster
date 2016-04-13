package jkord.dynablaster.web.dto;

import jkord.core.domain.User;
import jkord.dynablaster.entity.Lobby;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class LobbyDTO extends LobbyInfoDTO implements Serializable {

    private boolean isActive = false;
    private ZonedDateTime createdAt;
    private List<LobbyUserDTO> users;
    private boolean isOwner;

    public LobbyDTO(Lobby lobby, User owner) {
        super(lobby);

        createdAt = lobby.getCreatedAt();
        isActive = lobby.isActive();
        this.isOwner = lobby.getOwner().equals(owner);

        users = new ArrayList<>(4);
        lobby.getUsers().forEach(lobbyUser -> users.add(new LobbyUserDTO(lobbyUser)));
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

    public boolean isOwner() {
        return isOwner;
    }
}
