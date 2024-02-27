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
package eu.wepa.hivemqextensions.metricspertopic.configuration.entities;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.annotations.Nullable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;


@SuppressWarnings("FieldMayBeFinal")
@XmlRootElement(name = "metrics-per-topic-extension-configuration")
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class ExtensionConfig {

    public static final @NotNull String TRUE = "true";
    public static final @NotNull String FALSE = "false";

    public static final @NotNull String DEFAULT_VERBOSE = FALSE;

    @XmlElement(name = "verbose", required = false, defaultValue = DEFAULT_VERBOSE)
    private @Nullable String verbose = DEFAULT_VERBOSE;

    public boolean isVerbose() {
        if (verbose == null) {
            verbose = DEFAULT_VERBOSE;
        }

        return verbose.equalsIgnoreCase(TRUE);
    }

    public String getVerbose() {
        return verbose;
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose;
    }

    @Override
    public @NotNull String toString() {
        return "extension_config{" + "verbose=" + verbose + "}";
    }
}
