package ru.baskaeva.dressshop.services;

import ru.baskaeva.dressshop.models.Order;
import ru.baskaeva.dressshop.models.User;

import java.util.List;

public interface OrderService {
    List<Order> getOrder(User user);
    Order createOrder(User user);
    Order changeStatus(Long id, User user, Order.Status status);
}
