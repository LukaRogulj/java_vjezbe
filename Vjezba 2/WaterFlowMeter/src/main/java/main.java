import org.eclipse.paho.client.mqttv3.MqttException;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws MqttException, IOException {
        WaterFlowMeter wfm = new WaterFlowMeter();

        wfm.sendMessages();
        wfm.serialize();

    }
}
