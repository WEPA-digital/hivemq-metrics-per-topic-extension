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
import com.hivemq.extension.sdk.api.annotations.ThreadSafe;
import eu.wepa.hivemqextensions.metricspertopic.configuration.entities.ExtensionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

@ThreadSafe
class ConfigurationXmlParser {

    private static final @NotNull Logger LOG = LoggerFactory.getLogger(ConfigurationXmlParser.class);

    //jaxb context is thread safe
    private final @NotNull JAXBContext jaxb;

    ConfigurationXmlParser() {
        try {
            jaxb = JAXBContext.newInstance(ExtensionConfig.class);
        } catch (final JAXBException e) {
            LOG.error("Error in the File Config Extension. Could not initialize XML parser", e);
            throw new RuntimeException(e);
        }
    }

    @NotNull ExtensionConfig unmarshalExtensionConfig(final @NotNull File file) throws IOException {
        try {
            final Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            return (ExtensionConfig) unmarshaller.unmarshal(file);
        } catch (final JAXBException e) {
            throw new IOException(e);
        }
    }
}
