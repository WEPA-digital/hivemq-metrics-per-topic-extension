/*
 * Copyright 2023-present MaibornWolff GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.wepa.hivemqextensions.metricspertopic.initializer;

import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.client.ClientContext;
import com.hivemq.extension.sdk.api.client.parameter.InitializerInput;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor;
import com.hivemq.extension.sdk.api.services.intializer.ClientInitializer;
import eu.wepa.hivemqextensions.metricspertopic.configuration.entities.ExtensionConfig;
import eu.wepa.hivemqextensions.metricspertopic.initializer.interceptors.PublishInboundInterceptorImpl;
import eu.wepa.hivemqextensions.metricspertopic.initializer.interceptors.PublishOutboundInterceptorImpl;

public class ClientInitializerImpl implements ClientInitializer {

    private final @NotNull MetricRegistry metricRegistry;
    private final @NotNull ExtensionConfig config;

    public ClientInitializerImpl(
            final @NotNull MetricRegistry metricRegistry,
            final @NotNull ExtensionConfig config
    ) {
        this.metricRegistry = metricRegistry;
        this.config = config;
    }

    @Override
    public void initialize(@NotNull InitializerInput initializerInput, @NotNull ClientContext clientContext) {
        // Add Publish Inbound Interceptor
        PublishInboundInterceptor publishInboundInterceptor = new PublishInboundInterceptorImpl(metricRegistry, config);
        clientContext.addPublishInboundInterceptor(publishInboundInterceptor);

        // Add Publish Outbound Interceptor
        PublishOutboundInterceptor publishOutboundInterceptor = new PublishOutboundInterceptorImpl(metricRegistry, config);
        clientContext.addPublishOutboundInterceptor(publishOutboundInterceptor);
    }
}
