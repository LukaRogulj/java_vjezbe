import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.util.List;

public class WaterFlowMeterMqtt {
    private String brokerURL;
    private String topic;
    private String protocol;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    //endregion

    WaterFlowMeterMqtt(){
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

    public String serializeMessage(Sensor sensor) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String msg = mapper.writeValueAsString(sensor);
        return msg;
    }

    private void sendPOST() throws IOException{
//        String result;
        HttpPost post = new HttpPost("https://httpbin.org/post");

        for (Sensor it : this.sensors) {
            it.setSensorValue();
            String message = serializeMessage(it);
            post.setEntity(new StringEntity(message));

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);

            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        }
    }

    public void sendMessages() throws MqttException, IOException {

        if (this.protocol.equals("http")) {
            sendPOST();
        } else {
            MqttClient client = new MqttClient(brokerURL, MqttClient.generateClientId());
            MqttMessage message = new MqttMessage();
            while (true) sendMessage(client, message);
        }

    }
}
