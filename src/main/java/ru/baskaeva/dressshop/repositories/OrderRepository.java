package ru.baskaeva.dressshop.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.baskaeva.dressshop.models.Order;
import ru.baskaeva.dressshop.models.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUser(User user);
    Order findByUserAndId(User user, Long id);
}
