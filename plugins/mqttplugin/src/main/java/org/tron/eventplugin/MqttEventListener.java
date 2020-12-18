package org.tron.eventplugin;

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
    String clientId  	= "MqttClient";
    MemoryPersistence persistence = new MemoryPersistence();
	
    MqttClient mqttClient = null;
   
    String block = "";
	String solidityTrigger = "";
	String transaction = "";
	
    
    @Override
    public void setServerAddress(String address) {
        // empty implementation
    }

    @Override
    public void setTopic(int eventType, String topic) {
        // empty implementation
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
        {}
        System.out.println("Connected");
    }

    @Override
    public void handleBlockEvent(Object data) {
    	try {
		String Data = (String)data;
		Data = Data.replace("{", "").replace("}", "");
		String[] pairs = Data.split(",");
		for (int i=0;i<pairs.length;i++) {
		    if(pairs[i].contains(Constant.TIME_STAMP) == true || pairs[i].contains(Constant.TRIGGER_NAME) || pairs[i].contains("latestSolidifiedBlockNumber"))
		    	solidityTrigger = solidityTrigger + pairs[i] + " ; ";
		    if(pairs[i].contains(Constant.BLOCK_NUMBER) || pairs[i].contains(Constant.BLOCK_HASH))
		    	block = block + pairs[i]+ " ; " ;
		    if(pairs[i].contains(Constant.TRANSACTION_SIZE) || pairs[i].contains(Constant.TRANSACTION_LIST))
		    	transaction = transaction + pairs[i]+ " ; ";
		}
	    Function.publish(Constant.BLOCK_TRIGGER_NAME, block, qos, mqttClient);
        Function.publish(Constant.TRANSACTION_TRIGGER_NAME, transaction, qos, mqttClient);
        Function.publish(Constant.SOLIDITY_TRIGGER_NAME, solidityTrigger, qos, mqttClient);
        block = "";
    	solidityTrigger = "";
    	transaction = "";
    	}
        catch(Exception ex)
    	{}
        System.out.println("Message published");
    }

    @Override
    public void handleTransactionTrigger(Object data) {
    }

    @Override
    public void handleSolidityTrigger(Object data) {
    }

    @Override
    public void handleSolidityLogTrigger(Object data) {
    }

    @Override
    public void handleSolidityEventTrigger(Object data) {
    }

    @Override
    public void handleContractLogTrigger(Object data) {
    }

    @Override
    public void handleContractEventTrigger(Object data) {
    }

}
