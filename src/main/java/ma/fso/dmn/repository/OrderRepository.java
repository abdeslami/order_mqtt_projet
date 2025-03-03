package ma.fso.dmn.repository;

import ma.fso.dmn.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
