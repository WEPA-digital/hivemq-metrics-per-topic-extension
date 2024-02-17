package com.wepa.hivemqextensions.metricspertopic.config;

import com.hivemq.extension.sdk.api.annotations.NotNull;

import java.util.Properties;

public class TopicsMetricsConfig {

    static final @NotNull String TRUE = "true";
    static final @NotNull String FALSE = "false";
    public static final @NotNull String COMBINE_TOPICS = "combine-topics";

    private final @NotNull Properties properties;

    public TopicsMetricsConfig(final @NotNull Properties properties) {
        this.properties = properties;
    }

    public boolean isCombineTopics() {
        return getForKey(COMBINE_TOPICS);
    }

    private boolean getForKey(final @NotNull String key) {
        return properties.getProperty(key, TRUE).equalsIgnoreCase(TRUE);
    }
}
