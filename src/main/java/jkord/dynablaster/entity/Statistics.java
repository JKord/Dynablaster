package jkord.dynablaster.entity;

import jkord.core.domain.BaseEntity;
import jkord.core.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "statistics")
public class Statistics extends BaseEntity implements Serializable {

    @OneToMany
    protected User userWon;

    @ManyToMany(fetch = FetchType.EAGER)
    protected Set<User> usersPlayed;

    @Column(name = "points")
    @ElementCollection
    protected Map<Long, Integer> pointsUsers;

    public Statistics(Set<User> usersPlayed) {
        this.usersPlayed = usersPlayed;

        pointsUsers = new HashMap<>();
        usersPlayed.forEach(user -> pointsUsers.put(user.getId(), 0));
    }

    public User getUserWon() {
        return userWon;
    }

    public void setUserWon(User userWon) {
        this.userWon = userWon;
    }

    public Set<User> getUsersPlayed() {
        return usersPlayed;
    }

    public void setUsersPlayed(Set<User> usersPlayed) {
        this.usersPlayed = usersPlayed;
    }

    public void addUserPlayed(User user) {
        this.usersPlayed.add(user);
    }

    public void removeUserPlayed(User user) {
        this.usersPlayed.remove(user);
    }

    public Map<Long, Integer> getPointsUsers() {
        return pointsUsers;
    }

    public void setPointsUsers(Map<Long, Integer> pointsUsers) {
        this.pointsUsers = pointsUsers;
    }

    public void setPointsUser(User user, int points) {
        this.pointsUsers.put(user.getId(), points);
    }

    public void addPointsUser(User user, int points) {
        this.pointsUsers.put(user.getId(), this.pointsUsers.get(user.getId()) + points);
    }

    public void removePointsUser(User user) {
        this.pointsUsers.remove(user.getId());
    }
}
