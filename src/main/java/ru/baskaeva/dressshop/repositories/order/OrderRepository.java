package ru.baskaeva.dressshop.repositories.order;

import org.springframework.data.repository.CrudRepository;
import ru.baskaeva.dressshop.models.order.Order;
import ru.baskaeva.dressshop.models.user.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUser(User user);
}
