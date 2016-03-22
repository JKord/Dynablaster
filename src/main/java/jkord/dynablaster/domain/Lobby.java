package jkord.dynablaster.domain;

import jkord.core.domain.BaseEntity;
import jkord.core.domain.User;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lobby")
public class Lobby extends BaseEntity implements Serializable {

    @Column(name = "count_users", length = 1)
    protected short countUsers = 0;

    @Column(name = "is_active", length = 1)
    protected boolean isActive = false;

    @CreatedDate
    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    protected Set<LobbyUser> lobbyUser = new HashSet<>(4);

    public short getCountUsers() {
        return countUsers;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addUser(User user) {
        countUsers++;
        lobbyUser.add(new LobbyUser(user));
    }

    public void removeUser(User user) {
        countUsers--;
        lobbyUser.remove(new LobbyUser(user));
    }
}
