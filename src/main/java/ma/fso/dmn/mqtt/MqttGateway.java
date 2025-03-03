package ma.fso.dmn.mqtt;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;

@IntegrationComponentScan
public interface MqttGateway {
    @Gateway(requestChannel = "mqttOutboundChannel")
    void sendToMqtt(String message);
}
