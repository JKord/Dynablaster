package jkord.pssn.web.rest;

import jkord.pssn.domain.User;
import jkord.pssn.service.SocialService;
import jkord.pssn.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.vkontakte.api.Group;
import org.springframework.social.vkontakte.api.VKontakte;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/social")
public class SocialResource {

    @Inject
    private SocialService socialService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> groups() {
        User user = userService.getUserWithAuthorities();
        MultiValueMap<String, Connection<?>> connections = socialService.getConnections(user.getLogin());
        VKontakte vk = (VKontakte) connections.getFirst("vkontakte").getApi();

        return vk.groupsOperations().getGroups();
    }
}
