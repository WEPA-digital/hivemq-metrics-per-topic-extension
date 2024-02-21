package com.wepa.hivemqextensions.metricspertopic.config;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static com.wepa.hivemqextensions.metricspertopic.config.TopicsMetricsConfig.*;

public class TopicsMetricsConfigReader {

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(TopicsMetricsConfigReader.class);

    public static final @NotNull String PROPERTIES_FILE_NAME = "topicsMetricsConfiguration.properties";

    private final @NotNull Properties properties;

    private final @NotNull File extensionHomeFolder;

    public TopicsMetricsConfigReader(final @NotNull File extensionHomeFolder) {
        this.extensionHomeFolder = extensionHomeFolder;
        this.properties = new Properties();
        setDefaults();
    }

    private void setDefaults() {
        properties.setProperty(COMBINE_TOPICS, TRUE);
        properties.setProperty(VERBOSE, FALSE);
    }

    public @NotNull Properties readProperties() {
        final File propertiesFile = new File(extensionHomeFolder, PROPERTIES_FILE_NAME);

        LOG.debug("Topics Messages Incoming and outgoing count Metrics Extension: Will try to read config properties from {}",
                PROPERTIES_FILE_NAME);

        if (!propertiesFile.canRead()) {
            LOG.info("Topics Messages Incoming and outgoing count Metrics Extension: Cannot read properties file {}",
                    propertiesFile.getAbsolutePath());
        } else {
            try (final InputStream is = new FileInputStream(propertiesFile)) {
                properties.load(is);
            } catch (final Exception e) {
                LOG.warn("Topics Messages Incoming and outgoing count Metrics Extension: Could not load properties file, reason {}",
                        e.getMessage());
            }
        }

        LOG.info("Topics Messages Incoming and outgoing count Metrics Extension: Properties initialized to: {}", properties);
        return properties;
    }
}
