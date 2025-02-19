/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.vkontakte.api.impl;

import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.vkontakte.api.VKontakte;

import static com.vk.api.sdk.client.Lang.EN;

/**
 * {@link VKontakte} implementation.
 *
 * @author vkolodrevskiy
 */
public class VKontakteTemplate extends AbstractOAuth2ApiBinding implements VKontakte {

    private final static Log log = LogFactory.getLog(VKontakteTemplate.class);

    private UserActor userActor;
    private ServiceActor serviceActor;
    private VkApiClient vkApiClient;

    private final Integer providerUserId;
    private final String email;
    private final String accessToken;
    private final Integer clientId;
    private final String clientSecret;
    private final Lang lang;

    public VKontakteTemplate() {
        this.providerUserId = null;
        this.accessToken = null;
        this.clientId = null;
        this.clientSecret = null;
        this.email = null;
        this.lang = EN;
        initialize();
    }

    public VKontakteTemplate(Integer providerUserId,
                             String email,
                             String accessToken,
                             Integer clientId,
                             String clientSecret,
                             Lang lang) {
        super(accessToken);
        this.providerUserId = providerUserId;
        this.email = email;
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.lang = lang;
        initialize();
    }

    public VKontakteTemplate(Integer providerUserId,
                             String email,
                             String accessToken,
                             Integer clientId,
                             String clientSecret) {
        super(accessToken);
        this.providerUserId = providerUserId;
        this.email = email;
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.lang = EN;
        initialize();
    }

    private void initialize() {
        userActor = new UserActor(providerUserId, accessToken);
        serviceActor = new ServiceActor(clientId, clientSecret, accessToken);
        vkApiClient = new VkApiClient(HttpTransportClient.getInstance());
    }

    @Override
    public ServiceActor getServiceActor() {
        return serviceActor;
    }

    @Override
    public UserActor getUserActor() {
        return userActor;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UserFull usersGet() {
        try {
            return vkApiClient.users()
                              .get(userActor)
                              .fields(Fields.values())
                              .lang(lang)
                              .execute()
                              .get(0);
        } catch (Exception e) {
            log.error("Error while getting current user info.", e);
        }
        return new UserFull();
    }
}
