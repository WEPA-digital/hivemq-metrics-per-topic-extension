/*
 * Copyright 2024-present WEPA GmbH
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
package eu.wepa.hivemqextensions.metricspertopic;

import com.codahale.metrics.Counter;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import eu.wepa.hivemqextensions.metricspertopic.configuration.entities.ExtensionConfig;

import java.util.HashMap;

public class MetricCounterHandler {

    private final HashMap<String, Counter> counters;
    private final ExtensionConfig config;

    private static MetricCounterHandler handler;

    private MetricCounterHandler(final @NotNull ExtensionConfig config) {
        this.config = config;
        counters = new HashMap<>();
    }

    public static MetricCounterHandler initHandler(final @NotNull ExtensionConfig config) {
        if (handler == null) {
            handler = new MetricCounterHandler(config);
        }

        return handler;
    }

    public HashMap<String, Counter> getCounters() {
        return counters;
    }

    public void put(String key, Counter counter) {
        counters.put(key, counter);
    }

    public void inc(String key) {
        final Counter counter = counters.get(key);

        if (counter != null) {
            counters.get(key).inc();
        }
    }

    public int size() {
        return counters.size();
    }

    @Override
    public String toString() {
        final int total = this.counters.keySet().size();
        final int totalInboundCounters = totalCountersPrefix(config.getMetricsNamePrefix().getInboundPublishMetricNamePrefix());
        final int totalOutboundCounters = totalCountersPrefix(config.getMetricsNamePrefix().getOutboundPublishMetricNamePrefix());

        return String.format("Metrics stats (total=%d, inbound=%d, outbound=%d)",
            total,
            totalInboundCounters,
            totalOutboundCounters
        );
    }

    private int totalCountersPrefix(String prefix) {
        return (int) this.counters
                .keySet()
                .stream()
                .filter(key -> key.startsWith(prefix))
                .count();
    }

}
