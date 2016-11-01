package mqtt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import mqtt.utility.MqttPublishSubscribeUtility;

@RestController
public class Controller {
	
	@RequestMapping("/light")
	public String light(){
		MqttPublishSubscribeUtility mqttPublishSubscribeUtility = new MqttPublishSubscribeUtility();
		 JsonObject payload = new JsonObject();
		 payload.addProperty("id", "Kaiiiiiiiis");
		 mqttPublishSubscribeUtility.mqttConnectNPublishNSubscribe(payload);
		 return "envoyer";
	}

}
