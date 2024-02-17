package com.wepa.hivemqextensions.metricspertopic.config;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TopicsMetricsConfigReaderTest {

    private final int totalAvailableFlags = 1;

    private final @NotNull List<String> defaultProperties = List.of(TopicsMetricsConfig.COMBINE_TOPICS);

    @Test
    void defaultPropertiesWhenNoPropertyFileInConfigFolder(@TempDir final @NotNull Path tempDir) {
        final Properties properties = new TopicsMetricsConfigReader(tempDir.toFile()).readProperties();

        assertEquals(properties.size(), totalAvailableFlags);
        assertTrue(properties.stringPropertyNames().containsAll(defaultProperties));
        assertTrue(defaultProperties.containsAll(properties.stringPropertyNames()));
    }

    @Test
    void nonEmptyPropertiesWhenPropertyFileInConfigFolder() {
        final String path = Objects.requireNonNull(getClass().getResource("/test-conf")).getPath();
        final Properties properties = new TopicsMetricsConfigReader(new File(path)).readProperties();

        assertEquals(properties.size(), totalAvailableFlags);
        assertTrue(properties.stringPropertyNames().containsAll(defaultProperties));
        assertTrue(defaultProperties.containsAll(properties.stringPropertyNames()));
    }
}
