import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
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
    }

    private void sendMessage(MqttClient client, MqttMessage message ) throws MqttException, IOException {
            client.connect();
            for (Sensor it : this.sensors) {
                it.setSensorValue();
                String temp = serializeMessage(it);
                message.setPayload(temp.getBytes());
                client.publish(topic, message);
            }
            client.disconnect();
    }

    public void sendMessages() throws MqttException, IOException {
        MqttClient client = new MqttClient(brokerURL, MqttClient.generateClientId());
        MqttMessage message = new MqttMessage();

        while (true) sendMessage(client, message);
    }

    public String serializeMessage(Sensor sensor) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String msg = mapper.writeValueAsString(sensor);
        return msg;
    }
}
