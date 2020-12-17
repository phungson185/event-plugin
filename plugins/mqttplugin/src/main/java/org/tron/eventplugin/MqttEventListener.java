package org.tron.eventplugin;

import java.util.HashMap;
import java.util.Map;

import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.common.logsfilter.IPluginEventListener;
import java.util.Objects;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Extension
public class MqttEventListener implements IPluginEventListener {

    private static final Logger log = LoggerFactory.getLogger(MqttEventListener.class);
    
    int qos             = 2;
    String broker       = "tcp://54.179.134.203:1883";
    String clientId     = "MqttClient";
    MemoryPersistence persistence = new MemoryPersistence();
	Map<String, String> myMap = new HashMap<String, String>();
	
    MqttClient mqttClient = null;
    MqttMessage message = null;
    
    String topic = "";
    String block = "";
	String solidityTrigger = "";
	String transaction = "";
	
    
    @Override
    public void setServerAddress(String address) {
    	
    }

    @Override
    public void setTopic(int eventType, String topic) {
    	
    }

    @Override
    public void setDBConfig(String dbConfig) {
        // empty implementation
    }
    @Override
    public void start() {
    	try
        {
    	mqttClient = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: "+broker);
        mqttClient.connect(connOpts);
        }
        catch(Exception ex)
        {	
        }
        System.out.println("Connected");
    }

    @Override
    public void handleBlockEvent(Object data) {
    	try {
    	String block = "";
        String solidityTrigger = "";
        String transaction = "";
		String Data = (String)data;
		String[] pairs = Data.split(",");
//		for (int i=0;i<pairs.length;i++) {
//			System.out.print("Publishing message: "+pairs[i]);
//			System.out.print("\n");
//		    if(pairs[i].contains("timeStamp") == true || pairs[i].contains("triggerName" ) || pairs[i].contains("latestSolidifiedBlockNumber"))
		   solidityTrigger = solidityTrigger + pairs[0] + " ; " +  pairs[1] + " ; " + pairs[5];
		   solidityTrigger = solidityTrigger.replace("{", "");
//		    if(pairs[i].contains("blockNumber") || pairs[i].contains("blockHash"))
		   block = block + pairs[2]+ " ; " + pairs[3];
//		    if(pairs[i].contains("transactionSize") || pairs[i].contains("transactionList"))
		   transaction = transaction + pairs[4]+ " ; " +pairs[6];
		   transaction = transaction.replace("}", "");
//		}
//		System.out.print("Publishing message: "+solidityTrigger);
        topic = "Block";
		System.out.println("Publishing message: "+block);
        message = new MqttMessage(block.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
        
        topic = "Transaction";
		System.out.println("Publishing message: "+transaction);
        message = new MqttMessage(transaction.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
        
        topic = "SolidityTrigger";
		System.out.println("Publishing message: "+solidityTrigger);
        message = new MqttMessage(solidityTrigger.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
        
    	}
    	catch(Exception ex)
    	{}
        System.out.println("Message published");
    }

    @Override
    public void handleTransactionTrigger(Object data) {
    	try {
    	String topic = "transactionTrigger";
		String Data = (String)data;
		System.out.println("Publishing message: "+Data);
        MqttMessage message = new MqttMessage(Data.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
    	}
    	catch(Exception ex)
    	{}
        System.out.println("Message published");
    }

    @Override
    public void handleSolidityTrigger(Object data) {
    	try {
    	String topic = "solidityTrigger";
		String Data = (String)data;
		System.out.println("Publishing message: "+Data);
        MqttMessage message = new MqttMessage(Data.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
    	}
    	catch(Exception ex)
    	{}
        System.out.println("Message published");
    }

    @Override
    public void handleSolidityLogTrigger(Object data) {
    	try {
    	String topic = "solidityLogTrigger";
		String Data = (String)data;
		System.out.println("Publishing message: "+Data);
        MqttMessage message = new MqttMessage(Data.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
    	}
    	catch(Exception ex)
    	{}
        System.out.println("Message published");
    }

    @Override
    public void handleSolidityEventTrigger(Object data) {
    	try {
    	String topic        = "solidityEventTrigger";
		String Data = (String)data;
		System.out.println("Publishing message: "+Data);
        MqttMessage message = new MqttMessage(Data.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
    	}
    	catch(Exception ex)
    	{}
        System.out.println("Message published");
    }

    @Override
    public void handleContractLogTrigger(Object data) {
    	try {
    	String topic        = "contractLogTrigger";
		String Data = (String)data;
		System.out.println("Publishing message: "+Data);
        MqttMessage message = new MqttMessage(Data.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
    	}
    	catch(Exception ex)
    	{}
        System.out.println("Message published");
    }

    @Override
    public void handleContractEventTrigger(Object data) {
    	try {
    	String topic        = "contractEventTrigger";
		String Data = (String)data;
		System.out.println("Publishing message: "+Data);
        MqttMessage message = new MqttMessage(Data.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
    	}
    	catch(Exception ex)
    	{}
        System.out.println("Message published");
    }
    
//    @Override
//    public void timeStamp(Object data) {
//    	String topic        = "timeStamp";
//		String Data = (String)data;
//		System.out.println("Publishing message: "+Data);
//        MqttMessage message = new MqttMessage(Data.getBytes());
//        message.setQos(qos);
//        mqttClient.publish(topic, message);
//        System.out.println("Message published");
//    }
}
