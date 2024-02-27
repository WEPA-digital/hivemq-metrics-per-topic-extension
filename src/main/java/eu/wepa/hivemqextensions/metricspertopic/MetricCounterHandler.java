/*
 * Copyright 2024-present MaibornWolff GmbH
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
import java.util.HashMap;

public class MetricCounterHandler {

    private final HashMap<String, Counter> counters;

    private static MetricCounterHandler handler;

    private MetricCounterHandler() {
        counters = new HashMap<>();
    }

    public static MetricCounterHandler initHandler() {
        if (handler == null) {
            handler = new MetricCounterHandler();
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
        counters.get(key).inc();
    }

    public int size() {
        return counters.size();
    }

    public String toString() {
        final int total = this.counters.keySet().size();
        final int totalInboundCounters = totalCountersPrefix(MetricsConstants.METRIC_INCOMING_PREFIX);
        final int totalOutboundCounters = totalCountersPrefix(MetricsConstants.METRIC_OUTGOING_PREFIX);

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
