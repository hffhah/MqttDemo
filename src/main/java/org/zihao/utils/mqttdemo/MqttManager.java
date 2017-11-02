package org.zihao.utils.mqttdemo;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * 
 * @author zihaozhu
 * @date 2017年10月28日
 */
public class MqttManager {
	private String mBrokenUrl;
	private String mClientId;

	private MqttClient mClient;
	private MqttConnectOptions mOptions;

	private String mUserName;
	private String mPsd;

	public MqttManager(String brokerUrl) {
		this.mBrokenUrl = brokerUrl;
		this.mClientId = MqttClient.generateClientId();

		try {
			this.mClient = new MqttClient(brokerUrl, this.mClientId, new MemoryPersistence());
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public MqttManager(String brokerUrl, String userName, String psd) {
		this.mBrokenUrl = brokerUrl;
		this.mClientId = MqttClient.generateClientId();
		this.mUserName = userName;
		this.mPsd = psd;

		initOptions();
	}

	private void initOptions() {
		try {
			mClient = new MqttClient(this.mBrokenUrl, this.mClientId, new MemoryPersistence());

			mOptions = new MqttConnectOptions();
			/** true: server clean conn session */
			mOptions.setCleanSession(true);
			mOptions.setUserName(mUserName);
			mOptions.setPassword(mPsd.toCharArray());
			mOptions.setConnectionTimeout(10);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public void subscribe(String topic) {
		try {
			mClient.setCallback(new PushCallback());

			if (mOptions == null) {
				mClient.connect();
			} else {
				mClient.connect(mOptions);
			}

			mClient.subscribe(topic);

		} catch (Exception e) {
			e.printStackTrace();
			disconnect();
		}
	}

	public void subscribe(String topic, PushCallback pushCallback) {
		try {
			mClient.setCallback(pushCallback);

			if (mOptions == null) {
				mClient.connect();
			} else {
				mClient.connect(mOptions);
			}

			mClient.subscribe(topic);

		} catch (Exception e) {
			e.printStackTrace();
			disconnect();
		}
	}

	public void publish(String topic, String message) {
		try {
			mClient.connect(mOptions);

			MqttTopic mqttTopic = mClient.getTopic(topic);
			MqttMessage mqttMessage = new MqttMessage();
			try {
				String s = new String(message.getBytes(), "UTF-8");
				mqttMessage.setPayload(s.getBytes());

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				mqttMessage.setPayload(message.getBytes());
			}

			mqttTopic.publish(mqttMessage);

		} catch (MqttException e) {
			e.printStackTrace();
			disconnect();
		}
	}

	public void disconnect() {
		try {
			mClient.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
