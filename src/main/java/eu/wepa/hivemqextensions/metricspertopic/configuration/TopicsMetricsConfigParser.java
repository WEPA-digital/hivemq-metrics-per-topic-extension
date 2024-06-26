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
package eu.wepa.hivemqextensions.metricspertopic.configuration;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.annotations.Nullable;
import eu.wepa.hivemqextensions.metricspertopic.configuration.entities.ExtensionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class TopicsMetricsConfigParser {

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(TopicsMetricsConfigParser.class);

    public static final @NotNull String EXTENSION_CONFIG_FILE_NAME = "conf/config.xml";

    private final @NotNull ConfigurationXmlParser configurationXmlParser = new ConfigurationXmlParser();
    private @Nullable ExtensionConfig extensionConfig;

    private final @NotNull File extensionHomeFolder;

    public TopicsMetricsConfigParser(final @NotNull File extensionHomeFolder) {
        this.extensionHomeFolder = extensionHomeFolder;
    }

    public @NotNull ExtensionConfig getConfig() {
        if (extensionConfig == null) {
            extensionConfig = read(new File(extensionHomeFolder, EXTENSION_CONFIG_FILE_NAME));
        }
        return extensionConfig;
    }

    private @NotNull ExtensionConfig read(final @NotNull File configFile) {
        final @NotNull ExtensionConfig defaultExtensionConfig = new ExtensionConfig();

        if (
            !configFile.exists()
            || !configFile.canRead()
            || configFile.length() == 0
        ) {

            LOG.warn("Unable to read Metrics per topic extension configuration file {}, using defaults {}.",
                    configFile.getAbsolutePath(),
                    defaultExtensionConfig);

            return defaultExtensionConfig;
        }

        try {
            final @NotNull ExtensionConfig config = configurationXmlParser.unmarshalExtensionConfig(configFile);

            return validate(config, defaultExtensionConfig);
        } catch (IOException e) {
            LOG.warn("Could not read Metrics per topic extension configuration file, reason: {}, using defaults {}.",
                    e.getMessage(),
                    defaultExtensionConfig);

            return defaultExtensionConfig;
        }
    }

    private @NotNull ExtensionConfig validate(
        final @NotNull ExtensionConfig config,
        final @NotNull ExtensionConfig defaultConfig
    ) {

        if (config.isVerbose() == null) {
            LOG.warn("Verbose value is null, set default value");
            config.setVerbose(defaultConfig.isVerbose());
        }

        if(config.getMetricsNamePrefix() == null) {
            LOG.warn("Metrics Name Prefix is null, set default prefixes");
            config.setMetricsNamePrefix(defaultConfig.getMetricsNamePrefix());
        }

        return config;
    }
}
