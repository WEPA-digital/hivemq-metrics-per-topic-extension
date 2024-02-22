package com.wepa.hivemqextensions.metricspertopic.configuration.entities;

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
