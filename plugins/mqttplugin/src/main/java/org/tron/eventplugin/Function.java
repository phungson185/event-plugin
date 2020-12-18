package org.tron.eventplugin;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class Function {
    static MqttMessage message = null;
	public static void publish(String topic, String messageEvent, int qos, MqttClient mqttClient) throws MqttPersistenceException, MqttException {
		System.out.println("Publishing message: "+messageEvent);
        message = new MqttMessage(messageEvent.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
	}
}