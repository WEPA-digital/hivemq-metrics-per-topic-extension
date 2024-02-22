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
package com.wepa.hivemqextensions.metricspertopic.configuration;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.wepa.hivemqextensions.metricspertopic.configuration.entities.ExtensionConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TopicsMetricsConfigReaderTest {

    @Test
    void test_defaultPropertiesWhenNoPropertyFileInConfigFolder(@TempDir final @NotNull Path tempDir) {
        final ExtensionConfig config = new TopicsMetricsConfigParser(tempDir.toFile()).getConfig();
        final ExtensionConfig defaultConfig = new ExtensionConfig();

        assertFalse(config.isVerbose());
        assertEquals(config.getVerbose(), defaultConfig.getVerbose());
    }

    @Test
    void test_nonEmptyPropertiesWhenPropertyFileInConfigFolder(@TempDir final @NotNull Path tempDir) throws IOException {
        final String extensionContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<metrics-per-topic-extension-configuration>\n" +
                "        <verbose>true</verbose>\n" +
                "</metrics-per-topic-extension-configuration>\n";
        Files.writeString(tempDir.resolve("config.xml"), extensionContent);

        final ExtensionConfig config = new TopicsMetricsConfigParser(tempDir.toFile()).getConfig();

        assertTrue(config.isVerbose());
    }

    @Test
    void test_invalidPropertiesWhenPropertyFileInConfigFolder(@TempDir final @NotNull Path tempDir) throws IOException {
        final String extensionContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<<metrics-per-topic-extension-configuration>\n" +
                "        <verbose>random</verbose>\n" +
                "</<metrics-per-topic-extension-configuration>\n";
        Files.writeString(tempDir.resolve("config.xml"), extensionContent);

        final ExtensionConfig config = new TopicsMetricsConfigParser(tempDir.toFile()).getConfig();

        assertFalse(config.isVerbose());
    }

    @Test
    void test_missingPropertiesWhenPropertyFileInConfigFolder(@TempDir final @NotNull Path tempDir) throws IOException {
        final String extensionContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<<metrics-per-topic-extension-configuration>\n" +
                "</<metrics-per-topic-extension-configuration>\n";
        Files.writeString(tempDir.resolve("config.xml"), extensionContent);

        final ExtensionConfig config = new TopicsMetricsConfigParser(tempDir.toFile()).getConfig();

        assertFalse(config.isVerbose());
    }
}
