package org.tron.eventplugin;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class Function {
	public static void publish(Object data, int qos, String topic, MqttClient mqttClient) throws MqttPersistenceException, MqttException {
		String Data = (String)data;
        MqttMessage message = new MqttMessage(Data.getBytes());
        message.setQos(qos);
		mqttClient.publish(topic, message);
	}
}