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

import com.codahale.metrics.Counter;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundOutput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundOutput;

public class TopicsInterceptor implements PublishInboundInterceptor, PublishOutboundInterceptor {

    final private Counter incomingMessagesCounter;
    final private Counter outgoingMessagesCounter;

    public TopicsInterceptor(Counter incomingMessagesCounter ,Counter outgoingMessagesCounter) {
        this.incomingMessagesCounter = incomingMessagesCounter;
        this.outgoingMessagesCounter = outgoingMessagesCounter;
    }

    @Override
    public void onInboundPublish(
            final @NotNull PublishInboundInput publishInboundInput,
            final @NotNull PublishInboundOutput publishInboundOutput
    ) {
        this.incomingMessagesCounter.inc();
    }

    @Override
    public void onOutboundPublish(
            @NotNull PublishOutboundInput publishOutboundInput,
            @NotNull PublishOutboundOutput publishOutboundOutput
    ) {
        this.outgoingMessagesCounter.inc();
    }
}
