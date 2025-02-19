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
package org.springframework.social.vkontakte.connect;

import com.vk.api.sdk.client.Lang;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.vkontakte.api.VKontakte;

import static com.vk.api.sdk.client.Lang.EN;

/**
 * VKontakte {@link org.springframework.social.connect.ConnectionFactory} implementation.
 *
 * @author vkolodrevskiy
 */
public class VKontakteConnectionFactory extends OAuth2ConnectionFactory<VKontakte> {

    public VKontakteConnectionFactory(String clientId, String clientSecret, Lang lang) {
        super("vkontakte", new VKontakteServiceProvider(clientId, clientSecret, lang), new VKontakteAdapter(lang));
    }

    public VKontakteConnectionFactory(String clientId, String clientSecret) {
        super("vkontakte", new VKontakteServiceProvider(clientId, clientSecret, EN), new VKontakteAdapter(EN));
    }

    @Override
    public boolean supportsStateParameter() {
        // vk.com does not send state parameter in it's OAuth2 callback
        // see https://github.com/vkolodrevskiy/spring-social-vkontakte/issues/14
        return false;
    }

    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        return ((VkAccessGrant) accessGrant).getUserId().toString();
    }
}
