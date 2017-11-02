package org.zihao.utils.mqttdemo;

/**
 * 
 * @author zihaozhu
 * @date 2017-11-02
 */
public class Client {

	static final String TOPIC = "sap/ind/app/andon";

	public static void main(String[] args) {
		MqttManager mqttManager = new MqttManager(Constant.MQTT_BROKER_URL, Constant.MQTT_USER, Constant.MQTT_PSD);
		mqttManager.subscribe(TOPIC, new PushCallback());
		
		while (true) {
			try {
				Thread.sleep(2000);
				mqttManager.publish(TOPIC, "it's a test string...");
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
