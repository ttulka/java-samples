package com.ttulka.samples.mqtt;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@RequiredArgsConstructor
public class MqttPublisher {

    private final @NonNull MqttClient client;
    private final @NonNull String topic;

    public void publish(byte[] message) {
        try {
            client.publish(topic, new MqttMessage(message));

        } catch (MqttException e) {
            throw new RuntimeException("Cannot publish a message", e);
        }
    }
}
