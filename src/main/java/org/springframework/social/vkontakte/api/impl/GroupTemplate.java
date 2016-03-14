package org.springframework.social.vkontakte.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.vkontakte.api.Group;
import org.springframework.social.vkontakte.api.GroupOperations;
import org.springframework.social.vkontakte.api.VKGenericResponse;
import org.springframework.social.vkontakte.api.VKontakteProfile;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Properties;

public class GroupTemplate extends AbstractVKOperations implements GroupOperations {

    private final RestTemplate restTemplate;

    public GroupTemplate(RestTemplate restTemplate, String accessToken, ObjectMapper objectMapper, boolean isAuthorizedForUser) {
        super(isAuthorizedForUser, accessToken, objectMapper);
        this.restTemplate = restTemplate;
    }

    public List<Group> getGroups() {


        return null;
    }

    public Group getGroup(String groupId) {
        Properties props = new Properties();
        props.put("fields", "description,members_count");
        URI uri = makeOperationURL("groups.getById", props);

        VKGenericResponse response = restTemplate.getForObject(uri, VKGenericResponse.class);
        checkForError(response);

        return null;
    }

    public PagedList<VKontakteProfile> getMemberProfiles(String groupId) {
        return null;
    }
}
