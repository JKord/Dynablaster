package org.springframework.social.vkontakte.api;

/**
 * Interface specifying a basic set of operations for interacting with VK.
 * Implemented by {@link org.springframework.social.vkontakte.api.impl.VKTemplate}.
 * @author JKord
 */
public interface VK extends VKontakte {

    /**
     * API for group operations.
     */
    GroupOperations groupOperations();
}
