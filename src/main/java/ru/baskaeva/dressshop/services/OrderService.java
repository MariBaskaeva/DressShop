package ru.baskaeva.dressshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.baskaeva.dressshop.dto.order.OrderDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.bag.BagProduct;
import ru.baskaeva.dressshop.models.order.Order;
import ru.baskaeva.dressshop.models.order.ProductHistory;
import ru.baskaeva.dressshop.models.product.ProductSize;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.repositories.bag.BagProductRepository;
import ru.baskaeva.dressshop.repositories.order.OrderRepository;
import ru.baskaeva.dressshop.repositories.order.ProductHistoryRepository;
import ru.baskaeva.dressshop.repositories.product.ProductRepository;
import ru.baskaeva.dressshop.repositories.product.ProductSizeRepository;
import ru.baskaeva.dressshop.services.interfaces.IOrderService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final BagProductRepository bagProductRepository;
    private final ProductHistoryRepository productHistoryRepository;
    private final ProductSizeRepository productSizeRepository;

    public List<Order> getOrder(User user) {
        return orderRepository.findByUser(user);
    }

    @Transactional
    public Order createOrder(User user, OrderDTO orderDTO) {
        List<BagProduct> bagProducts = bagProductRepository.getByUser(user);
        List<ProductHistory> productHistories = bagProducts.stream().map(p -> {
            ProductSize productSize = p.getProductSize();
            productSize.setCount(p.getProductSize().getCount() - p.getCount());
            productSizeRepository.save(productSize);
            return productHistoryRepository.save(
                    ProductHistory.builder()
                            .productInfo(p.getProductSize().getDetails().getProduct().getProductInfo())
                            .color(p.getProductSize().getDetails().getColor())
                            .image(p.getProductSize().getDetails().getImage())
                            .price(p.getProductSize().getDetails().getPrice())
                            .size(p.getProductSize().getSize())
                            .count(p.getCount())
                            .build());
        }).toList();
        Order order = Order.builder()
                .timestamp(System.currentTimeMillis())
                .products(productHistories)
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .dateTime(LocalDateTime.now().plus(5, ChronoUnit.DAYS))
                .totalCost(bagProducts.stream().mapToLong(x -> x.getCount() * x.getProductSize().getDetails().getPrice()).sum())
                .status(Order.Status.InProgress)
                .user(user)
                .build();

        return orderRepository.save(order);
    }

    public Order changeStatus(Long id, User user, Order.Status status) throws NoSuchProductException {
        Order order = orderRepository.findById(id).orElseThrow(NoSuchProductException::new);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
