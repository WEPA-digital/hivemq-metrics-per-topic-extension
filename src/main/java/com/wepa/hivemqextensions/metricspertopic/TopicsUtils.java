/*
 * Copyright 2023-present MaibornWolff GmbH
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
