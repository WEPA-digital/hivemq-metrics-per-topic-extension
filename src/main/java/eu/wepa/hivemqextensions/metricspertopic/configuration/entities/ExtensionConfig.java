/*
 * Copyright 2024-present WEPA Digital GmbH
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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;


@XmlRootElement(name = "metrics-per-topic-extension-configuration")
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtensionConfig {

    @XmlElement(type = Boolean.class, name = "verbose", required = false, defaultValue = "false")
    private @Nullable Boolean verbose = false;

    @XmlElement(name = "prefix-metrics-name", nillable = true)
    private @Nullable MetricsNamePrefix metricsNamePrefix;

    // Default Metrics prefixes
    public ExtensionConfig() {
        metricsNamePrefix = MetricsNamePrefix.getDefaultMetricsConfig();
    }

    public @Nullable Boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(final Boolean verbose) {
        this.verbose = verbose;
    }

    public @NotNull MetricsNamePrefix getMetricsNamePrefix() {
        return metricsNamePrefix;
    }

    public void setMetricsNamePrefix(final MetricsNamePrefix metricsNamePrefix) {
        this.metricsNamePrefix = metricsNamePrefix;
    }

    @Override
    public @NotNull String toString() {
        return "extension_config{" + "verbose=" + verbose + ", metricsNamePrefix="+ metricsNamePrefix + "}";
    }
}
