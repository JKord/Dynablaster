package org.springframework.social.vkontakte.api;

/**
 * Model class representing a Group.
 * @author JKord
 */
public class Group extends VKObject {

    private int id;

    private String name;

    private String screenName;

    private boolean isClosed;

    private String type;

    private boolean isAdmin;

    private boolean isMember;

    private String description;

    private Long membersCount;

    private String photo50;

    private String photo100;

    private String photo200;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public String getType() {
        return type;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isMember() {
        return isMember;
    }

    public String getDescription() {
        return description;
    }

    public Long getMembersCount() {
        return membersCount;
    }

    public String getPhoto50() {
        return photo50;
    }

    public String getPhoto100() {
        return photo100;
    }

    public String getPhoto200() {
        return photo200;
    }
}
