package ru.baskaeva.dressshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.Bag;
import ru.baskaeva.dressshop.models.Product;
import ru.baskaeva.dressshop.models.User;
import ru.baskaeva.dressshop.services.BagService;
import ru.baskaeva.dressshop.services.BagServiceImpl;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/bag")
public class BagController {
    private final BagService bagService;

    @GetMapping
    public Bag getBag(@AuthenticationPrincipal User user){
        return bagService.getBag(user);
    }

    @PostMapping("{id}")
    public Bag postProductToBag(@AuthenticationPrincipal User user, @PathVariable Long id) throws NoSuchProductException {
        return bagService.addProductToBag(user, id);
    }

    @DeleteMapping("{id}")
    public Bag deleteProductFromBag(@AuthenticationPrincipal User user, @PathVariable Long id) throws NoSuchProductException {
        return bagService.deleteFromBag(id, user);
    }

    @DeleteMapping
    public Bag deleteProductsFromBag(@AuthenticationPrincipal User user){
        return bagService.clearBag(user);
    }
}
