package com.wepa.hivemqextensions.metricspertopic;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.HashMap;

public class TopicsUtils {

    public static void addTopicCounter(
            String metricName,
            MetricRegistry metricRegistry,
            HashMap<String, Counter> counters
    ) {
        Counter counter = metricRegistry.counter(metricName);
        counters.put(metricName, counter);
    }

    public static String topicToValidMetricName(String topic, String prefix) {
        return prefix +  "." + topic.replace("/", ".");
    }
}
