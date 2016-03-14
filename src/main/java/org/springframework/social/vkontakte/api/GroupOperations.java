package org.springframework.social.vkontakte.api;

import org.springframework.social.facebook.api.PagedList;

import java.util.List;

/**
 * Model class representing a Group.
 * @author JKord
 */
public interface GroupOperations {

    /**
     *
     * @return List of user {@link Group} object
     */
    List<Group> getGroups();

    /**
     *
     * @param groupId the ID of the group
     * @return a {@link Group} object
     */
    Group getGroup(String groupId);


    PagedList<VKontakteProfile> getMemberProfiles(String groupId);
}
