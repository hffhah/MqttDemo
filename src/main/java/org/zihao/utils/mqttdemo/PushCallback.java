package org.zihao.utils.mqttdemo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 
 * @author zihaozhu
 * @date 2017年10月28日
 */
public class PushCallback implements MqttCallback {

	/**
	 * 丢失时重连
	 */
	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("---debug:连接断开");
	}

	/**
	 * 接收已经预订的发布 subscribe后得到的消息会执行到这里面
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("---debug:接收消息主题:" + topic);
		System.out.println("---debug:接收消息Qos:" + message.getQos());
		System.out.println("---debug:接收消息内容:" + new String(message.getPayload()));
	}

	/**
	 * 接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用 publish后会执行到这里
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("---debug:" + token.isComplete());
	}

}
