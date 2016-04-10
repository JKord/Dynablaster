package jkord.dynablaster.web.dto;

import jkord.dynablaster.entity.Lobby;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class LobbyDTO implements Serializable {

    private Long id;
    private String name;
    private short countUsers = 0;
    private boolean isActive = false;
    private ZonedDateTime createdAt;
    private List<LobbyUserDTO> users;
    private boolean isOwner;

    public LobbyDTO(Lobby lobby, boolean isOwner) {
        id = lobby.getId();
        name = lobby.getName();
        countUsers = lobby.getCountUsers();
        createdAt = lobby.getCreatedAt();
        isActive = lobby.isActive();
        this.isOwner = isOwner;

        users = new LinkedList<>();
        lobby.getUsers().forEach(lobbyUser -> users.add(new LobbyUserDTO(lobbyUser)));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public short getCountUsers() {
        return countUsers;
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
