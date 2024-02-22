package com.wepa.hivemqextensions.metricspertopic;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.client.parameter.ServerInformation;
import com.hivemq.extension.sdk.api.parameter.ExtensionInformation;
import com.hivemq.extension.sdk.api.parameter.ExtensionStartInput;
import com.hivemq.extension.sdk.api.parameter.ExtensionStartOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.mockito.Mockito.*;

public class TopicsMetricsExtensionsMainTest {
    private @NotNull TopicsMetricsExtensionMain topicsMetricsExtensionMain;
    private @NotNull ExtensionStartInput extensionStartInput;
    private @NotNull ExtensionStartOutput extensionStartOutput;

    @BeforeEach
    void setUp( ) {
        extensionStartInput = mock(ExtensionStartInput.class);
        extensionStartOutput = mock(ExtensionStartOutput.class);
        topicsMetricsExtensionMain = new TopicsMetricsExtensionMain();
    }

    @Test
    void test_extension_start_prevented_no_access_to_static_components() {
        final ExtensionInformation information = mock(ExtensionInformation.class);
        final ServerInformation serverInformation = mock(ServerInformation.class);

        when(extensionStartInput.getExtensionInformation()).thenReturn(information);
        when(extensionStartInput.getServerInformation()).thenReturn(serverInformation);
        when(information.getExtensionHomeFolder()).thenReturn(new File("some/not/existing/folder"));
        when(information.getName()).thenReturn("My Extension");

        topicsMetricsExtensionMain.extensionStart(extensionStartInput, extensionStartOutput);
        verify(extensionStartOutput).preventExtensionStartup("My Extension cannot be started");
    }

    @Test
    void test_extension_start_prevented_because_of_old_version() {
        when(extensionStartInput.getServerInformation()).thenThrow(NoSuchMethodError.class);

        topicsMetricsExtensionMain.extensionStart(extensionStartInput, extensionStartOutput);
        verify(extensionStartOutput).preventExtensionStartup("The HiveMQ version is not supported");
    }
}
