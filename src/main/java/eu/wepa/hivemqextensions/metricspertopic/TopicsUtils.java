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
import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.annotations.Nullable;

public class TopicsUtils {

    public static void addTopicCounter(
            String metricName,
            MetricRegistry metricRegistry,
            MetricCounterHandler counterHandler
    ) {
        Counter counter = metricRegistry.counter(metricName);
        counterHandler.put(metricName, counter);
    }

    public static final @NotNull String topicToValidMetricName(@Nullable String topic, @Nullable String prefix) throws Exception {
        // TODO: Create NotValidMetricNameException class extends Exception 
        topic = topic != null ? topic.replace("/", ".") : "";
        prefix = prefix != null ? prefix + "." : "";

        final String metric = prefix + topic;

        if (metric.length() == 0) {
            throw new Exception("please provide a valide prefix and topic name");
        }

        return metric;
    }
}
