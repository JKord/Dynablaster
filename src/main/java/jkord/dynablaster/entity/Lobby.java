package jkord.dynablaster.entity;

import jkord.core.domain.BaseEntity;
import jkord.core.domain.User;
import org.hibernate.validator.constraints.Length;
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

    @NotNull
    @Length(min = 3, max = 255)
    @Column(name = "name", length = 255)
    protected String name;

    @Column(name = "count_users", length = 1)
    protected short countUsers = 0;

    @Column(name = "is_active", length = 1)
    protected boolean isActive = false;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    protected ZonedDateTime createdAt = ZonedDateTime.now();

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    protected Set<LobbyUser> lobbyUsers = new HashSet<>(4);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCountUsers() {
        return countUsers;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<LobbyUser> getUsers() {
        return lobbyUsers;
    }

    public void addUser(User user) {
        countUsers++;
        lobbyUsers.add(new LobbyUser(user, this));
    }

    public void removeUser(User user) {
        countUsers--;
        lobbyUsers.remove(new LobbyUser(user, this));
    }
}
