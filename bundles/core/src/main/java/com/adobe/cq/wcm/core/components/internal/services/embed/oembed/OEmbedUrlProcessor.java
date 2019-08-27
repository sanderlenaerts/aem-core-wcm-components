/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2019
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
package com.adobe.cq.wcm.core.components.internal.services.embed.oembed;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.cq.wcm.core.components.internal.services.embed.ProcessorResultImpl;
import com.adobe.cq.wcm.core.components.models.embed.Embed;
import com.adobe.cq.wcm.core.components.models.embed.oembed.OEmbedClient;
import com.adobe.cq.wcm.core.components.models.embed.oembed.OEmbedResponse;

@Component(service = Embed.UrlProcessor.class)
public class OEmbedUrlProcessor implements Embed.UrlProcessor {

    protected static final String NAME = "oembed";

    @Reference
    protected OEmbedClient oEmbedClient;

    @Override
    public Result process(String url) {
        if (oEmbedClient == null || StringUtils.isEmpty(url)) {
            return null;
        }
        String provider = oEmbedClient.getProvider(url);
        if (StringUtils.isEmpty(provider)) {
            return null;
        }

        OEmbedResponse oEmbedResponse = oEmbedClient.getResponse(provider, url);
        if (oEmbedResponse == null) {
            return null;
        }
        return new ProcessorResultImpl(
                NAME,
                new HashMap<String, Object>() {{
                    put("provider", provider);
                    put("response", oEmbedResponse);
                }});
    }

}