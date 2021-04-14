import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.File;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws MqttException, IOException {
        WaterFlowMeter wfm = new ObjectMapper().readValue(new File("sensor.json"), WaterFlowMeter.class);

        wfm.sendMessages();

    }
}

