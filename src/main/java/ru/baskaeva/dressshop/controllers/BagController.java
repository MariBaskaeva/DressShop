package ru.baskaeva.dressshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.baskaeva.dressshop.dto.bag.BagProductDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.bag.BagProduct;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.services.interfaces.IBagService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/bag")
public class BagController {
    private final IBagService bagService;

    @GetMapping
    public List<BagProductDTO> getBag(@AuthenticationPrincipal User user){
        return bagService.getBag(user);
    }

    @PostMapping("{id}")
    public BagProduct postProductToBag(@AuthenticationPrincipal User user, @PathVariable Long id) throws NoSuchProductException {
        return bagService.addProductToBag(user, id);
    }

    @DeleteMapping("{id}")
    public void deleteProductFromBag(@AuthenticationPrincipal User user, @PathVariable Long id) throws NoSuchProductException {
        bagService.deleteFromBag(id, user);
    }

    @DeleteMapping
    public void deleteProductsFromBag(@AuthenticationPrincipal User user){
        bagService.clearBag(user);
    }
}
