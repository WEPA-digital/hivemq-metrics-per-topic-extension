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

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.testcontainers.hivemq.HiveMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TopicsInterceptorIT {

    @Container
    final @NotNull HiveMQContainer extension = new HiveMQContainer(DockerImageName.parse("hivemq/hivemq-ce").withTag("latest"))
            .withExtension(MountableFile.forClasspathResource("hivemq-metrics-per-topic-extension"));

    @Test
    @Timeout(value = 5, unit = TimeUnit.MINUTES)
    void test_payload_modified() throws InterruptedException {
        final Mqtt5BlockingClient client = Mqtt5Client.builder()
                .identifier("wepa-client")
                .serverPort(extension.getMqttPort())
                .buildBlocking();
        client.connect();

        final Mqtt5BlockingClient.Mqtt5Publishes publishes = client.publishes(MqttGlobalPublishFilter.ALL);
        client.subscribeWith().topicFilter("wepa/cas/paper/pm13/vacuum_system").send();

        client.publishWith().topic("wepa/cas/paper/pm13/vacuum_system").payload("Good Bye World!".getBytes(StandardCharsets.UTF_8)).send();

        final Mqtt5Publish received = publishes.receive();
        assertEquals("Hello From Vacuum System!", new String(received.getPayloadAsBytes(), StandardCharsets.UTF_8));
        publishes.close();
    }
}