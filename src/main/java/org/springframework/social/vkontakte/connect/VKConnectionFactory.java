package org.springframework.social.vkontakte.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.vkontakte.api.VKontakte;

/**
 * Vk ConnectionFactory.
 * @author JKord
 */
public class VKConnectionFactory extends OAuth2ConnectionFactory<VKontakte> {

    public VKConnectionFactory(String clientId, String clientSecret) {
        super("vkontakte", new VKServiceProvider(clientId, clientSecret), new VKontakteAdapter());
    }

    @Override public boolean supportsStateParameter() {
        return false;
    }
}
