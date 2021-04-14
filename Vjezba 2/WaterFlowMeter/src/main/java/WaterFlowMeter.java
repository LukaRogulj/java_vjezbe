import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WaterFlowMeter {
    private String brokerURL;
    private String topic;
    private List<Sensor> sensors;

    //region Getters and Setters
    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
    //endregion

    WaterFlowMeter(){
        brokerURL = "tcp://localhost:1883";
        topic = "iot_data";
        sensors = new ArrayList<>();
    }

    private void setSensorData(){
        this.sensors.add(new Sensor("Water temperature", 10, -32660, 32660, "Celsius"));
        this.sensors.add(new Sensor("Water pressure", 1000, 0, 65336, "Bar"));
        this.sensors.add(new Sensor("Daly consumption", 0, 0, 65336, "Liters"));
        this.sensors.add(new Sensor("Yearly consumption", 10, 0, 65336, "Cubic meters"));
    }

    private void sendMessage(MqttClient client, MqttMessage message) throws MqttException {

        for (Sensor it : this.sensors) {
            it.setSensorValue();
            client.connect();
            message.setPayload((it.getName() + " " + it.getSensorValue() + " " + it.getUnit()).getBytes());
            client.publish(topic, message);
            client.disconnect();
        }
    }

    public void sendMessages() throws MqttException {
        setSensorData();
        MqttClient client = new MqttClient(brokerURL, MqttAsyncClient.generateClientId());
        MqttMessage message = new MqttMessage();
        while(true){
            sendMessage(client, message);
        }


    }
    public void serialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File("serializeSensor.json"), this);
    }
}
