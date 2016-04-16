package jkord.dynablaster.web.dto;

import jkord.dynablaster.entity.LobbyUser;
import java.io.Serializable;

public class LobbyUserDTO implements Serializable {
    private UserDTO user;
    private boolean isActive;

    public LobbyUserDTO(LobbyUser lobbyUser) {
        user = new UserDTO(lobbyUser.getUser());
        isActive = lobbyUser.isActive();
    }

    public UserDTO getUser() {
        return user;
    }

    public boolean isActive() {
        return isActive;
    }
}
