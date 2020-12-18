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
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
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
    		mqttClient.connect(connOpts);
        }
        catch(MqttException e)
        {
			e.printStackTrace();
        }
        System.out.println("Connected");
    }

    @Override
    public void handleBlockEvent(Object data) {
    	if (Objects.isNull(data)){
            return;
        }
    	try {
			Function.publish(data, qos, Constant.BLOCK_TRIGGER_NAME, mqttClient);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void handleTransactionTrigger(Object data) {
    	if (Objects.isNull(data)){
            return;
        }
    	try {
			Function.publish(data, qos, Constant.TRANSACTION_TRIGGER_NAME, mqttClient);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void handleSolidityTrigger(Object data) {
    	if (Objects.isNull(data)){
            return;
        }
    	try {
			Function.publish(data, qos, Constant.SOLIDITY_TRIGGER_NAME, mqttClient);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void handleSolidityLogTrigger(Object data) {
    	if (Objects.isNull(data)){
            return;
        }
    	try {
			Function.publish(data, qos, Constant.SOLIDITYLOG_TRIGGER_NAME, mqttClient);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void handleSolidityEventTrigger(Object data) {
    	if (Objects.isNull(data)){
            return;
        }
    	try {
			Function.publish(data, qos, Constant.SOLIDITYEVENT_TRIGGER_NAME, mqttClient);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void handleContractLogTrigger(Object data) {
    	if (Objects.isNull(data)){
            return;
        }
    	try {
			Function.publish(data, qos, Constant.CONTRACTLOG_TRIGGER_NAME, mqttClient);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void handleContractEventTrigger(Object data) {
    	if (Objects.isNull(data)){
            return;
        }
    	try {
			Function.publish(data, qos, Constant.CONTRACTEVENT_TRIGGER_NAME, mqttClient);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

}
