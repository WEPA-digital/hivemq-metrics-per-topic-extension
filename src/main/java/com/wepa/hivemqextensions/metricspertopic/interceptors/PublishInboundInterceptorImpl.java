package com.wepa.hivemqextensions.metricspertopic.interceptors;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundOutput;
import com.wepa.hivemqextensions.metricspertopic.TopicsUtils;
import com.wepa.hivemqextensions.metricspertopic.config.TopicsMetricsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class PublishInboundInterceptorImpl implements PublishInboundInterceptor {

    private final HashMap<String, Counter> counters;
    private final TopicsMetricsConfig config;
    private final MetricRegistry metricRegistry;

    private static final @NotNull Logger log = LoggerFactory.getLogger(PublishInboundInterceptorImpl.class);
    private static final String METRIC_NAME_PREFIX = "eu.wepa.hivemq.messages.incoming.count";

    public PublishInboundInterceptorImpl(
            final @NotNull MetricRegistry metricRegistry,
            final @NotNull TopicsMetricsConfig config
    ) {
        this.metricRegistry = metricRegistry;
        this.config = config;
        counters = new HashMap<>();
    }

    @Override
    public void onInboundPublish(
            @NotNull PublishInboundInput publishInboundInput,
            @NotNull PublishInboundOutput publishInboundOutput
    ) {
        String topic = publishInboundInput.getPublishPacket().getTopic();
        String metricName = TopicsUtils.topicToValidMetricName(topic, METRIC_NAME_PREFIX);

        // if counter not exist than, add it to counters.
        if (!counters.containsKey(metricName)) {
            if (config.isVerbose()) {
                log.info("No Metric Found For Topic: {}", topic);
                log.info("Create new Metric {} For Topic: {}", metricName, topic);
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
