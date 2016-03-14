package org.springframework.social.vkontakte.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.vkontakte.api.*;
import org.springframework.social.vkontakte.api.impl.json.VKontakteModule;

import java.util.LinkedList;
import java.util.List;

public class VKTemplate extends AbstractOAuth2ApiBinding implements VK {

    protected UsersOperations usersOperations;
    protected WallOperations wallOperations;
    protected FriendsOperations friendsOperations;
    protected FeedOperations feedOperations;
    protected GroupOperations groupOperations;

    protected ObjectMapper objectMapper;

    protected final String accessToken;
    protected final String uid;

    public VKTemplate(String accessToken, String uid) {
        super(accessToken);
        this.accessToken = accessToken;
        this.uid = uid;
        initialize();
    }

    private void initialize() {
        registerJsonModule();
        getRestTemplate().setErrorHandler(new VKontakteErrorHandler());
        initSubApis();
    }

    private void registerJsonModule() {
        List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

                List<MediaType> mTypes = new LinkedList<MediaType>(jsonConverter.getSupportedMediaTypes());
                mTypes.add(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
                jsonConverter.setSupportedMediaTypes(mTypes);

                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new VKontakteModule());
                jsonConverter.setObjectMapper(objectMapper);
            }
        }
    }

    protected void initSubApis() {
        usersOperations = new UsersTemplate(getRestTemplate(), accessToken, uid, objectMapper, isAuthorized());
        friendsOperations = new FriendsTemplate(getRestTemplate(), accessToken, objectMapper, isAuthorized());
        wallOperations = new WallTemplate(getRestTemplate(), accessToken, objectMapper, isAuthorized());
        feedOperations = new FeedTemplate(getRestTemplate(), accessToken, objectMapper, isAuthorized());
        groupOperations = new GroupTemplate(getRestTemplate(), accessToken, objectMapper, isAuthorized());
    }

    @Override
    public UsersOperations usersOperations() {
        return usersOperations;
    }

    @Override
    public WallOperations wallOperations() {
        return wallOperations;
    }

    @Override
    public FriendsOperations friendsOperations() {
        return friendsOperations;
    }

    @Override
    public FeedOperations feedOperations() {
        return feedOperations;
    }

    @Override
    public GroupOperations groupOperations() {
        return groupOperations;
    }
}
