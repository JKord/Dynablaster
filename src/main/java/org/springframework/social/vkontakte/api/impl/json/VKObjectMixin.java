package org.springframework.social.vkontakte.api.impl.json;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Annotated mixin to add Jackson annotations to VkObject.
 * @author JKord
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class VKObjectMixin {
    @JsonAnySetter
    abstract void add(String key, Object value);
}
