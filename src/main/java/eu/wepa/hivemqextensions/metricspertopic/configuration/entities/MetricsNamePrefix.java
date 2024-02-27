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
package eu.wepa.hivemqextensions.metricspertopic.configuration.entities;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.annotations.Nullable;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "prefix-metrics-name")
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.FIELD)
public class MetricsNamePrefix {

    private static final String METRIC_INBOUND_PREFIX = "com.hivemq.messages.inbound.count";
    private static final String METRIC_OUTBOUND_PREFIX = "com.hivemq.messages.outbound.count";

    @XmlElement(name = "publish-inbound-metric-prefix", required = false, defaultValue = METRIC_INBOUND_PREFIX)
    private @Nullable String inboundPublishMetricNamePrefix = METRIC_INBOUND_PREFIX;

    @XmlElement(name = "publish-outbound-metric-prefix", required = false, defaultValue = METRIC_OUTBOUND_PREFIX)
    private @Nullable String outboundPublishMetricNamePrefix = METRIC_OUTBOUND_PREFIX;

    @SuppressWarnings("unused")
    public MetricsNamePrefix() {
    }

    public MetricsNamePrefix(
            final @Nullable String inboundPublishMetricNamePrefix,
            final @Nullable String outboundPublishMetricNamePrefix
    ) {
        this.inboundPublishMetricNamePrefix = inboundPublishMetricNamePrefix;
        this.outboundPublishMetricNamePrefix = outboundPublishMetricNamePrefix;
    }

    public static MetricsNamePrefix getDefaultMetricsConfig() {
        return new MetricsNamePrefix(METRIC_INBOUND_PREFIX, METRIC_OUTBOUND_PREFIX);
    }

    public String getInboundPublishMetricNamePrefix() {
        return inboundPublishMetricNamePrefix;
    }

    public void setInboundPublishMetricNamePrefix(String inboundPublishMetricNamePrefix) {
        this.inboundPublishMetricNamePrefix = inboundPublishMetricNamePrefix;
    }

    public String getOutboundPublishMetricNamePrefix() {
        return outboundPublishMetricNamePrefix;
    }

    public void setOutboundPublishMetricNamePrefix(String outboundPublishMetricNamePrefix) {
        this.outboundPublishMetricNamePrefix = outboundPublishMetricNamePrefix;
    }

    @Override
    public @NotNull String toString() {
        return "metrics names prefix{" + "inboundPublishMetricNamePrefix=" + inboundPublishMetricNamePrefix + ", outboundPublishMetricNamePrefix=" + outboundPublishMetricNamePrefix + '}';
    }
}
