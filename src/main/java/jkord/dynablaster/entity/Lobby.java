package jkord.dynablaster.entity;

import jkord.core.domain.BaseEntity;
import jkord.core.domain.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lobby")
@NamedEntityGraph(name = "graph.Lobby.lobbyUsers",
    attributeNodes = @NamedAttributeNode(value = "lobbyUsers", subgraph = "lobbyUsers"),
    subgraphs = @NamedSubgraph(name = "lobbyUsers", attributeNodes = @NamedAttributeNode("lobby")))
public class Lobby extends BaseEntity {

    @NotNull
    @Length(min = 3, max = 255)
    @Column(name = "name", length = 255)
    protected String name;

    @Column(name = "count_users", length = 1)
    protected short countUsers = 0;

    @Column(name = "is_active", length = 1)
    protected boolean isActive = false;

    @ManyToOne
    protected User owner;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    protected ZonedDateTime createdAt = ZonedDateTime.now();

    @OneToMany(cascade=CascadeType.ALL)
    protected List<LobbyUser> lobbyUsers = new ArrayList<>(4);

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

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<LobbyUser> getUsers() {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
