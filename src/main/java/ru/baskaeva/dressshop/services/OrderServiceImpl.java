package ru.baskaeva.dressshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.baskaeva.dressshop.models.Bag;
import ru.baskaeva.dressshop.models.Order;
import ru.baskaeva.dressshop.models.ProductHistory;
import ru.baskaeva.dressshop.models.User;
import ru.baskaeva.dressshop.repositories.BagRepository;
import ru.baskaeva.dressshop.repositories.OrderRepository;
import ru.baskaeva.dressshop.repositories.ProductHistoryRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final BagRepository bagRepository;
    private final ProductHistoryRepository productHistoryRepository;

    public List<Order> getOrder(User user) {
        return orderRepository.findByUser(user);
    }

    @Transactional
    public Order createOrder(User user) {
        Bag bag = bagRepository.findByUser(user).orElseThrow(RuntimeException::new);

        Order order = Order.builder()
                .dateOfCreation(LocalDateTime.now())
                .dateOfDelivery(LocalDateTime.now().plus(5, ChronoUnit.DAYS))
                .totalCost(bag.getProducts().stream().mapToLong(x -> x.getCount() * x.getProduct().getCost()).sum())
                .user(user)
                .status(Order.Status.InProgress)
                .build();

        bag.getProducts().forEach(x -> {
            ProductHistory history = ProductHistory.fromProduct(x.getProduct());
            history.setCount(x.getCount());
            order.addProduct(productHistoryRepository.save(history));
        });

        return orderRepository.save(order);
    }

    public Order changeStatus(Long id, User user, Order.Status status) {
        Order order = orderRepository.findByUserAndId(user, id);
        order.setStatus(status);

        return orderRepository.save(order);
    }
}
