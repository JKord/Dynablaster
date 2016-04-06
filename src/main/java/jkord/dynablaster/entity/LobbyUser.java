package jkord.dynablaster.entity;

import jkord.core.domain.BaseEntity;
import jkord.core.domain.User;

import javax.persistence.*;

@Entity
@Table(name = "lobby_user")
public class LobbyUser extends BaseEntity {

    @ManyToOne
    protected User user;

    @Column(name = "is_active", length = 1)
    protected boolean isActive = false;

    public LobbyUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

        return user.equals(o);
    }
}
