package com.wepa.hivemqextensions.metricspertopic.interceptors;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;

public class PublishOutboundInterceptorImpl implements PublishOutboundInterceptor {

    private Counter outgoingMessagesCounter;
    private static final @NotNull Logger log = LoggerFactory.getLogger(PublishOutboundInterceptorImpl.class);

    public PublishOutboundInterceptorImpl(final @NotNull MetricRegistry metricRegistry) {

        final SortedMap<String, Counter> countersOutgoingMessages = metricRegistry.getCounters(MetricFilter.contains("com.wepa.messages.outgoing.count"));

        //we expect a single result here
        if (!countersOutgoingMessages.isEmpty()) {
            outgoingMessagesCounter = countersOutgoingMessages.values().iterator().next();
        }
    }

    @Override
    public void onOutboundPublish(@NotNull PublishOutboundInput publishOutboundInput, @NotNull PublishOutboundOutput publishOutboundOutput) {
        log.info("Outbound Message From Topic: {}", publishOutboundInput.getPublishPacket().getTopic());
        outgoingMessagesCounter.inc();
    }
}
