package jkord.dynablaster.entity;

import jkord.core.domain.BaseEntity;
import jkord.core.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "lobby_user")
public class LobbyUser extends BaseEntity {

    @ManyToOne
    protected User user;

    @ManyToOne
    protected Lobby lobby;

    @Column(name = "is_active", length = 1)
    protected boolean isActive = false;

    public LobbyUser() { }

    public LobbyUser(User user, Lobby lobby) {
        this.user = user;
        this.lobby = lobby;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LobbyUser lobbyUser = (LobbyUser) o;

        return user.getId().equals(lobbyUser.getUser().getId())
            && lobby.getId().equals(lobbyUser.getLobby().getId());
    }
}
