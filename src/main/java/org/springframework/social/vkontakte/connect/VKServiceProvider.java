package org.springframework.social.vkontakte.connect;

import org.springframework.social.vkontakte.api.VKontakte;
import org.springframework.social.vkontakte.api.impl.VKTemplate;

/**
 * VK ServiceProvider implementation.
 * @author JKord
 */
public class VKServiceProvider extends VKontakteServiceProvider {
    public VKServiceProvider(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }

    public VKontakte getApi(String accessToken) {
        VKontakteOAuth2Template v = (VKontakteOAuth2Template) getOAuthOperations();
        return new VKTemplate(accessToken, v.getUid());
    }
}
