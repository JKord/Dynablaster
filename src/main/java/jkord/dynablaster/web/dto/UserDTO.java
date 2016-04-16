package jkord.dynablaster.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;;
import jkord.core.domain.User;

public class UserDTO implements Serializable {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;

    public UserDTO(User user) {
        id = user.getId();
        login = user.getLogin();
        firstName = user.getFirstName();
        lastName = user.getLastName();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty
    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}
