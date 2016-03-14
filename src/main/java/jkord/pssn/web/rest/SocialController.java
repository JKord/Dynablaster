package jkord.pssn.web.rest;

import jkord.pssn.domain.User;
import jkord.pssn.service.SocialService;

import jkord.pssn.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.vkontakte.api.Group;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import org.springframework.social.vkontakte.api.VK;
import org.springframework.social.vkontakte.api.VKontakteProfile;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/social")
public class SocialController {
    private final Logger log = LoggerFactory.getLogger(SocialController.class);

    @Inject
    private SocialService socialService;

    @Inject
    private ProviderSignInUtils providerSignInUtils;

    @Inject
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public RedirectView signUp(WebRequest webRequest, @CookieValue(
        name = "NG_TRANSLATE_LANG_KEY", required = false, defaultValue = "\"en\"") String langKey)
    {
        try {
            Connection<?> connection = providerSignInUtils.getConnectionFromSession(webRequest);
            socialService.createSocialUser(connection, langKey.replace("\"", ""));
            return new RedirectView(URIBuilder.fromUri("/#/social-register/" + connection.getKey().getProviderId())
                .queryParam("success", "true")
                .build().toString(), true);
        } catch (Exception e) {
            log.error("Exception creating social user: ", e);
            return new RedirectView(URIBuilder.fromUri("/#/social-register/no-provider")
                .queryParam("success", "false")
                .build().toString(), true);
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> info() {
        User user = userService.getUserWithAuthorities();
        MultiValueMap<String, Connection<?>> connections = socialService.getConnections(user.getLogin());

        VK vk = (VK) connections.getFirst("vkontakte").getApi();

        return vk.groupOperations().getGroups();
    }

}
