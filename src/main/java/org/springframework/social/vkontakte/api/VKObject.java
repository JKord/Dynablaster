package org.springframework.social.vkontakte.api;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Base class for all VK types.
 * Offers an extraData property for carrying any data in response from VK that won't be otherwise mapped to any properties of the subclass.
 * @author JKord
 */
public abstract class VKObject {

    private Map<String, Object> extraData;

    public VKObject() {
        this.extraData = new HashMap<String, Object>();
    }

    /**
     * @return Any fields in response from Facebook that are otherwise not mapped to any properties.
     */
    public Map<String, Object> getExtraData() {
        return extraData;
    }

    /**
     * {@link JsonAnySetter} hook. Called when an otherwise unmapped property is being processed during JSON deserialization.
     * @param key The property's key.
     * @param value The property's value.
     */
    protected void add(String key, Object value) {
        extraData.put(key, value);
    }

}
