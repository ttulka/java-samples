package com.ttulka.samples.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttApplication {

    public static void main(String[] args) throws MqttException {
        MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
        try {
            client.connect();

            MqttPublisher publisher = new MqttPublisher(client, "test1");
            publisher.publish("Hello world from Java".getBytes());

        } finally {
            client.disconnect();
        }
    }
}
