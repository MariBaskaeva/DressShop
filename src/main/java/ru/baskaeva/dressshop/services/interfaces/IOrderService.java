package ru.baskaeva.dressshop.services.interfaces;

import ru.baskaeva.dressshop.dto.order.OrderDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.order.Order;
import ru.baskaeva.dressshop.models.user.User;

import java.util.List;

public interface IOrderService {
    List<Order> getOrder(User user);
    Order createOrder(User user, OrderDTO orderDTO);
    Order changeStatus(Long id, User user, Order.Status status) throws NoSuchProductException;
}
