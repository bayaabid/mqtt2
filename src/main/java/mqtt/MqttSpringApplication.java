package mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.google.gson.JsonObject;


import mqtt.utility.MqttPublishSubscribeUtility;

@SpringBootApplication
public class MqttSpringApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(MqttSpringApplication.class, args);
		MqttPublishSubscribeUtility gateway = context.getBean(MqttPublishSubscribeUtility.class);
		JsonObject payload = new JsonObject();
		gateway.mqttConnectNPublishNSubscribe(payload);
	}
	//@Bean
	public MqttPublishSubscribeUtility  kais(){
		MqttPublishSubscribeUtility mqttPublishSubscribeUtility = new MqttPublishSubscribeUtility();
		return mqttPublishSubscribeUtility ;
	}
}
