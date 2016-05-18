package org.nsf.domotic.control.mqttlisteners;

import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;

public class PublisherListener extends AbstractInterceptHandler {

    @Override
    public void onPublish(InterceptPublishMessage msg) {
        System.out.println("Received on topic: " + msg.getTopicName() + " content: " + new String(msg.getPayload().array()));
    }
}