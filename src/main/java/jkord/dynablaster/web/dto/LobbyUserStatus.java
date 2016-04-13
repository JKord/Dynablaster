package jkord.dynablaster.web.dto;

public class LobbyUserStatus {
    private Long lobbyId;
    private boolean active;

    public LobbyUserStatus() { }

    public LobbyUserStatus(Long lobbyId, boolean active) {
        this.lobbyId = lobbyId;
        this.active = active;
    }

    public Long getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(Long lobbyId) {
        this.lobbyId = lobbyId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
