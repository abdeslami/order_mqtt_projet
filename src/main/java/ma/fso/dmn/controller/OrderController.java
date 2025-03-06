package ma.fso.dmn.controller;

import jakarta.validation.Valid;
import ma.fso.dmn.model.Order;
import ma.fso.dmn.mqtt.MqttPublisher;
import ma.fso.dmn.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final MqttPublisher mqttPublisher;

    public OrderController(OrderService orderService, MqttPublisher mqttPublisher) {
        this.orderService = orderService;
        this.mqttPublisher = mqttPublisher;
    }


    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        try {
            logger.info("Création d'une nouvelle commande : {}", order);
            Order savedOrder = orderService.saveOrder(order);
            mqttPublisher.publish(savedOrder);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la commande : {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des commandes : {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        try {
            Optional<Order> order = orderService.getOrderById(id);
            return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de la commande (ID : {}) : {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @Valid @RequestBody Order order) {
        try {
            if (!orderService.getOrderById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            order.setId(id);
            Order updatedOrder = orderService.saveOrder(order);
            mqttPublisher.publish(updatedOrder);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de la commande (ID : {}) : {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        try {
            if (!orderService.getOrderById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }
            orderService.deleteOrder(id);

            Map<String, String> response = new HashMap<>();
            response.put("id", id);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de la commande (ID : {}) : {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
