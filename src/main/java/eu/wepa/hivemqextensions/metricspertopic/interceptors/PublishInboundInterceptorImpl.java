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
package eu.wepa.hivemqextensions.metricspertopic.interceptors;

import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundOutput;
import eu.wepa.hivemqextensions.metricspertopic.MetricCounterHandler;
import eu.wepa.hivemqextensions.metricspertopic.MetricsConstants;
import eu.wepa.hivemqextensions.metricspertopic.TopicsUtils;
import eu.wepa.hivemqextensions.metricspertopic.configuration.entities.ExtensionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishInboundInterceptorImpl implements PublishInboundInterceptor {

    private final MetricCounterHandler counterHandler;
    private final ExtensionConfig config;
    private final MetricRegistry metricRegistry;

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(PublishInboundInterceptorImpl.class);

    public PublishInboundInterceptorImpl(
            final @NotNull MetricRegistry metricRegistry,
            final @NotNull ExtensionConfig config
    ) {
        this.metricRegistry = metricRegistry;
        this.config = config;
        counterHandler = MetricCounterHandler.initHandler();
    }

    @Override
    public void onInboundPublish(
            @NotNull PublishInboundInput publishInboundInput,
            @NotNull PublishInboundOutput publishInboundOutput
    ) {
        String topic = publishInboundInput.getPublishPacket().getTopic();
        String metricName = TopicsUtils.topicToValidMetricName(topic, MetricsConstants.METRIC_INCOMING_PREFIX);

        // if counter not exist than, add it to counters.
        if (!counterHandler.getCounters().containsKey(metricName)) {
            if (config.isVerbose()) {
                LOG.info("No Metric Found For Topic: {}", topic);
                LOG.info("Create new Metric {} For Topic: {}", metricName, topic);
            }

            TopicsUtils.addTopicCounter(
                metricName,
                metricRegistry,
                counterHandler
            );
        }

        counterHandler.inc(metricName);
    }
}
