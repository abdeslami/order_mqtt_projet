package ma.fso.dmn.mqtt;

import ma.fso.dmn.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttPublisher {

    private static final Logger logger = LoggerFactory.getLogger(MqttPublisher.class);

    @Autowired
    private MqttGateway mqttGateway;

    @Autowired
    private ObjectMapper objectMapper;

    public void publish(Order order) {
        try {
            String jsonOrder = objectMapper.writeValueAsString(order);
            mqttGateway.sendToMqtt(jsonOrder);
            logger.info("Message publié avec succès : {}", jsonOrder);

        } catch (JsonProcessingException e) {
            logger.error("Erreur lors de la sérialisation de la commande en JSON : {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi du message via MQTT : {}", e.getMessage());
        }
    }
}
