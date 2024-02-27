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

import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.ExtensionMain;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.parameter.*;
import com.hivemq.extension.sdk.api.services.Services;
import com.hivemq.extension.sdk.api.services.intializer.ClientInitializer;
import com.hivemq.extension.sdk.api.services.intializer.InitializerRegistry;
import eu.wepa.hivemqextensions.metricspertopic.configuration.TopicsMetricsConfigParser;
import eu.wepa.hivemqextensions.metricspertopic.configuration.entities.ExtensionConfig;
import eu.wepa.hivemqextensions.metricspertopic.initializer.ClientInitializerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TopicsMetricsExtensionMain implements ExtensionMain {

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(TopicsMetricsExtensionMain.class);

    @Override
    public void extensionStart(
            final @NotNull ExtensionStartInput extensionStartInput,
            final @NotNull ExtensionStartOutput extensionStartOutput
    ) {
        try {
            extensionStartInput.getServerInformation();
        } catch (final NoSuchMethodError e) {
            // only a version that is not supported will throw this exception
            extensionStartOutput.preventExtensionStartup("The HiveMQ version is not supported");
            return;
        }

        try {
            final TopicsMetricsConfigParser configReader =
                    new TopicsMetricsConfigParser(
                        extensionStartInput.getExtensionInformation()
                            .getExtensionHomeFolder());
            ExtensionConfig config = configReader.getConfig();

            final MetricRegistry metricRegistry = Services.metricRegistry();

            initializeClient(metricRegistry, config);

            final ExtensionInformation extensionInformation = extensionStartInput.getExtensionInformation();
            LOG.info("Extension started {}: {}", extensionInformation.getName(), extensionInformation.getVersion());

            if (config.isVerbose()) {
                // every 30 seconds log the amount of counters that are created
                debugCounters(extensionInformation);
            }

        } catch (final Exception e) {
            extensionStartOutput.preventExtensionStartup(extensionStartInput.getExtensionInformation().getName() +
                    " cannot be started");

            LOG.error("Exception thrown at extension start: ", e);
        }
    }

    @Override
    public void extensionStop(
            final @NotNull ExtensionStopInput extensionStopInput,
            final @NotNull ExtensionStopOutput extensionStopOutput
    ) {
        final ExtensionInformation extensionInformation = extensionStopInput.getExtensionInformation();
        LOG.info("Extension Stopped " + extensionInformation.getName() + ":" + extensionInformation.getVersion());
    }

    private void initializeClient(
            final @NotNull MetricRegistry metricRegistry,
            final @NotNull ExtensionConfig config
    ) {
        final InitializerRegistry initializerRegistry = Services.initializerRegistry();
        final ClientInitializer clientInitializer = new ClientInitializerImpl(metricRegistry, config);

        initializerRegistry.setClientInitializer(clientInitializer);
    }

    private void debugCounters(final ExtensionInformation extensionInformation) {
        final MetricCounterHandler counterHandler = MetricCounterHandler.initHandler();
        Services.extensionExecutorService().scheduleAtFixedRate(
            () -> LOG.info("{} - {}", extensionInformation.getName(), counterHandler.toString()),
            30,
            30,
            TimeUnit.SECONDS
        );
    }
}