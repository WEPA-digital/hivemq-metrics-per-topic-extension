package com.wepa.hivemqextensions.metricspertopic.initializer.interceptors;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundOutput;
import com.wepa.hivemqextensions.metricspertopic.TopicsUtils;
import com.wepa.hivemqextensions.metricspertopic.configuration.entities.ExtensionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class PublishOutboundInterceptorImpl implements PublishOutboundInterceptor {

    private final HashMap<String, Counter> counters;
    private final ExtensionConfig config;
    private final MetricRegistry metricRegistry;

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(PublishOutboundInterceptorImpl.class);
    private static final String METRIC_NAME_PREFIX = "eu.wepa.hivemq.messages.outgoing.count";

    public PublishOutboundInterceptorImpl(
            final @NotNull MetricRegistry metricRegistry,
            final @NotNull ExtensionConfig config
    ) {
        this.metricRegistry = metricRegistry;
        this.config = config;
        counters = new HashMap<>();
    }

    @Override
    public void onOutboundPublish(
        @NotNull PublishOutboundInput publishOutboundInput, 
        @NotNull PublishOutboundOutput publishOutboundOutput
    ) {
        String topic = publishOutboundInput.getPublishPacket().getTopic();
        String metricName = TopicsUtils.topicToValidMetricName(topic, METRIC_NAME_PREFIX);

        // if counter not exist than, add it to counters.
        if (!counters.containsKey(metricName)) {
            if (config.isVerbose()) {
                LOG.info("No Metric Found For Topic: {}", topic);
                LOG.info("Create new Metric {} For Topic: {}", metricName, topic);
            }

            TopicsUtils.addTopicCounter(
                metricName,
                metricRegistry,
                counters
            );
        }

        counters.get(metricName).inc();
    }
}
