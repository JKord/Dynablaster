package jkord.dynablaster.web.dto;

import jkord.dynablaster.entity.Lobby;

public class LobbyInfoDTO {
    private Long id;
    private String name;
    private short countUsers = 0;

    public LobbyInfoDTO(Lobby lobby) {
        id = lobby.getId();
        name = lobby.getName();
        countUsers = lobby.getCountUsers();
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
}
