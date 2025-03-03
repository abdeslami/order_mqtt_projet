package ma.fso.dmn.mqtt;


import ma.fso.dmn.model.Order;
import ma.fso.dmn.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(String message) {
        try {
            Order order = objectMapper.readValue(message, Order.class);
            orderService.saveOrder(order);
            System.out.println("Order received and saved: " + order);
        } catch (JsonProcessingException e) {
            System.err.println("Error deserializing message: " + e.getMessage());
        }
    }
}