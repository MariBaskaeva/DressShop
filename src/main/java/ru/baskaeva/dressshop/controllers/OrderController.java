package ru.baskaeva.dressshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.baskaeva.dressshop.dto.order.OrderDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.order.Order;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.services.interfaces.IOrderService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final IOrderService orderService;

    @GetMapping
    private List<Order> getOrder(@AuthenticationPrincipal User user) {
        return orderService.getOrder(user);
    }

    @PostMapping
    private Order createOrder(@AuthenticationPrincipal User user,@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(user, orderDTO);
    }

    @PatchMapping("{id}")
    private Order changeStatus(@AuthenticationPrincipal User user, @PathVariable Long id, @RequestParam Order.Status status) throws NoSuchProductException {
        return orderService.changeStatus(id, user, status);
    }
}
