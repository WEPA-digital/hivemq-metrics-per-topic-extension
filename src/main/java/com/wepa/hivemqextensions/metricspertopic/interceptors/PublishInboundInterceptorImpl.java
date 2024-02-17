package com.wepa.hivemqextensions.metricspertopic.interceptors;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundOutput;
import com.wepa.hivemqextensions.metricspertopic.config.TopicsMetricsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public class PublishInboundInterceptorImpl implements PublishInboundInterceptor {

    private static final @NotNull Logger log = LoggerFactory.getLogger(PublishInboundInterceptorImpl.class);
    private Counter incomingMessagesCounter;

    public PublishInboundInterceptorImpl(
            final @NotNull MetricRegistry metricRegistry,
            final @NotNull TopicsMetricsConfig config
    ) {

        final SortedMap<String, Counter> countersIncomingMessages = metricRegistry.getCounters(MetricFilter.contains("com.wepa.messages.incoming.count"));

        //we expect a single result here
        if (!countersIncomingMessages.isEmpty()) {
            incomingMessagesCounter = countersIncomingMessages.values().iterator().next();
        }
    }

    @Override
    public void onInboundPublish(
            @NotNull PublishInboundInput publishInboundInput,
            @NotNull PublishInboundOutput publishInboundOutput
    ) {
        log.info("InBound Message From Topic: {}", publishInboundInput.getPublishPacket().getTopic());
        incomingMessagesCounter.inc();
    }
}
