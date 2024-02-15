/*
 * Copyright 2018-present HiveMQ GmbH
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
import com.hivemq.extension.sdk.api.ExtensionMain;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.parameter.*;
import com.hivemq.extension.sdk.api.services.Services;
import com.hivemq.extension.sdk.api.services.intializer.ClientInitializer;
import com.hivemq.extension.sdk.api.services.intializer.InitializerRegistry;
import com.wepa.hivemqextensions.metricspertopic.initializer.ClientInitializerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TopicsMetricsExtensionMain implements ExtensionMain {

    private static final @NotNull Logger log = LoggerFactory.getLogger(TopicsMetricsExtensionMain.class);

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
            final MetricRegistry metricRegistry = Services.metricRegistry();
            Counter incomingMessagesCounter = metricRegistry.counter("com.wepa.messages.incoming.count");
            Counter outgoingMessagesCounter = metricRegistry.counter("com.wepa.messages.outgoing.count");

            addClientLifecycleEventListener(metricRegistry);

            // TODO: ONly for testing purpose, every 10 seconds log the amount of messages sent and received per topic
            Services.extensionExecutorService().scheduleAtFixedRate(() -> {
                log.info("Currently Total incoming messages count {}", incomingMessagesCounter.getCount());
                log.info("Currently Total outgoing messages count {}", outgoingMessagesCounter.getCount());
            }, 10, 10, TimeUnit.SECONDS);

            final ExtensionInformation extensionInformation = extensionStartInput.getExtensionInformation();
            log.info("Extension started " + extensionInformation.getName() + ":" + extensionInformation.getVersion());

        } catch (final Exception e) {
            log.error("Exception thrown at extension start: ", e);
        }
    }

    @Override
    public void extensionStop(
            final @NotNull ExtensionStopInput extensionStopInput,
            final @NotNull ExtensionStopOutput extensionStopOutput
    ) {
        final ExtensionInformation extensionInformation = extensionStopInput.getExtensionInformation();
        log.info("Extension Stopped " + extensionInformation.getName() + ":" + extensionInformation.getVersion());
    }

    private void addClientLifecycleEventListener(final MetricRegistry metricRegistry) {

        final InitializerRegistry initializerRegistry = Services.initializerRegistry();
        final ClientInitializer clientInitializer = new ClientInitializerImpl(metricRegistry);

        initializerRegistry.setClientInitializer(clientInitializer);
    }
}