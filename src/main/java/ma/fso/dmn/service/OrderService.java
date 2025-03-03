package ma.fso.dmn.service;

import ma.fso.dmn.model.Order;
import ma.fso.dmn.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        if (orderRepository.existsById(order.getId())) {
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order with ID " + order.getId() + " does not exist.");
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}