package com.wepa.hivemqextensions.metricspertopic.initializer;

import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.client.ClientContext;
import com.hivemq.extension.sdk.api.client.parameter.InitializerInput;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor;
import com.hivemq.extension.sdk.api.services.intializer.ClientInitializer;
import com.wepa.hivemqextensions.metricspertopic.config.TopicsMetricsConfig;
import com.wepa.hivemqextensions.metricspertopic.interceptors.PublishInboundInterceptorImpl;
import com.wepa.hivemqextensions.metricspertopic.interceptors.PublishOutboundInterceptorImpl;

public class ClientInitializerImpl implements ClientInitializer {

    private final @NotNull MetricRegistry metricRegistry;
    private final @NotNull TopicsMetricsConfig config;

    public ClientInitializerImpl(
            final @NotNull MetricRegistry metricRegistry,
            final @NotNull TopicsMetricsConfig config
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
