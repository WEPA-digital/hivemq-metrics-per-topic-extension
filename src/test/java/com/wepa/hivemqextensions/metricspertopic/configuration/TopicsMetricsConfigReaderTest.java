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
                "        <verbose>blabla</verbose>\n" +
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
