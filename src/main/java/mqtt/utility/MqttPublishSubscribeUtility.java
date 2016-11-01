package mqtt.utility;

import java.nio.charset.Charset;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
@Component
public class MqttPublishSubscribeUtility {

	private final static String PROPERTIES_FILE_NAME = "/mqtt.properties";
	Properties properties = new Properties();

	public MqttClient mqttClient() throws MqttException {

		MemoryPersistence persistence = new MemoryPersistence();

		try {

			properties.load(MqttPublishSubscribeUtility.class.getResourceAsStream(PROPERTIES_FILE_NAME));

		} catch (Exception e) {
			System.out.println("Le fichier inexistant" + PROPERTIES_FILE_NAME);
			System.exit(1);
		}
		System.out.println("Details de connection MQTT");
		MqttClient mqttClient = new MqttClient(properties.getProperty("BROKER_URL"),
				properties.getProperty("CLIENT_ID"), persistence);
		MqttConnectOptions connectOptions = new MqttConnectOptions();
		connectOptions.setCleanSession(true);
		mqttClient.connect(connectOptions);

		return mqttClient;

	}
	
	public void mqttConnectNPublishNSubscribe(JsonObject payload){
		
		try {
			MqttClient  mqttClient =  mqttClient();
			System.out.println("Publish message = " + payload.toString());
			mqttClient.subscribe(properties.getProperty("TOPIC_NAME"),1);
			MqttMessage message = new MqttMessage(payload.toString().getBytes(Charset.forName("UTF-8")));
			if(properties.getProperty("QOS")!=null){
				message.setQos(Integer.parseInt(properties.getProperty("QOS")));
			}
			mqttClient.setCallback(new MqttCallback() {
				
				@Override
				public void messageArrived(String arg0, MqttMessage message) throws Exception {
					System.out.println("ddddd" +message.toString());
					
				}
				
				@Override
				public void deliveryComplete(IMqttDeliveryToken arg0) {
					System.out.println("livaraison complete");
					
				}
				
				@Override
				public void connectionLost(Throwable arg0) {
					System.out.println("connection perdu");
					
				}
			});
			mqttClient.publish(properties.getProperty("TOPIC_NAME"), message);
			
	
			System.out.println("message publie");
			System.out.println("Disconnect from brocker");
			//mqttClient.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			System.out.println("Raison" + e.getReasonCode());
			System.out.println("msg"+e.getMessage());
			System.out.println("local "+e.getLocalizedMessage());
			System.out.println("cause "+e.getCause());
			System.out.println("except "+e);
			e.printStackTrace();
		}
		
	}

}
