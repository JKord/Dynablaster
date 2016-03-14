package org.springframework.social.vkontakte.api.impl.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Annotated mixin to add Jackson annotations to Group.
 * @author JKord
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GroupMixin extends VKObjectMixin {

    @JsonProperty("id")
    int id;

    @JsonProperty("name")
    String name;

    @JsonProperty("screen_name")
    String screenName;

    @JsonProperty("is_closed")
    boolean isClosed;

    @JsonProperty("type")
    String type;

    @JsonProperty("is_admin")
    boolean isAdmin;

    @JsonProperty("is_member")
    boolean isMember;

    @JsonProperty("members_count")
    String membersCount;

    @JsonProperty("description")
    String description;

    @JsonProperty("photo_50")
    String photo50;

    @JsonProperty("photo_100")
    String photo100;

    @JsonProperty("photo_200")
    String photo200;
}
