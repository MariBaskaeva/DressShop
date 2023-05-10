package ru.baskaeva.dressshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.baskaeva.dressshop.models.Order;
import ru.baskaeva.dressshop.models.User;
import ru.baskaeva.dressshop.services.OrderService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    private List<Order> getOrder(@AuthenticationPrincipal User user) {
        return orderService.getOrder(user);
    }

    @PostMapping
    private Order createOrder(@AuthenticationPrincipal User user) {
        return orderService.createOrder(user);
    }

    @PatchMapping("{id}")
    private Order changeStatus(@AuthenticationPrincipal User user, @PathVariable Long id, @RequestParam Order.Status status) {
        return orderService.changeStatus(id, user, status);
    }
}
