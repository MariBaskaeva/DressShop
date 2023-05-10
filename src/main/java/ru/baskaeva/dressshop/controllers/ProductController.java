package ru.baskaeva.dressshop.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.baskaeva.dressshop.dto.ProductCreateDTO;
import ru.baskaeva.dressshop.dto.ProductDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.Product;
import ru.baskaeva.dressshop.models.User;
import ru.baskaeva.dressshop.services.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;


@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public Page<ProductDTO> getAllProducts(@RequestParam String search, @RequestParam Integer page,
                                           @RequestParam Integer limit, @AuthenticationPrincipal User user) {

        return productService.getProducts(search, page, limit, user);
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDTO postProduct(@RequestBody ProductCreateDTO product) {
        log.info("{}", product);
        return new ProductDTO(productService.createProduct(product), product.getIsFavourite());
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable long id, @AuthenticationPrincipal User user) throws Exception {
        return productService.getProduct(id, user);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable long id) {
        productService.removeProduct(id);
    }

    //Переделать
    @PutMapping("/products/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDTO putProduct(@PathVariable long id,
                                 @RequestBody ProductCreateDTO product,
                                 @AuthenticationPrincipal User user) {
        Product updateProduct = productService.updateProduct(id, new Product());

        return new ProductDTO();
    }

    @PostMapping("/like/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void likeProduct(@PathVariable long productId, @AuthenticationPrincipal User user) throws NoSuchProductException {
        log.info("{}", user);
        productService.likeProduct(productId, user);
    }

    @DeleteMapping("/like/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void dislikeProduct(@PathVariable long productId, @AuthenticationPrincipal User user) throws NoSuchProductException {
        productService.dislikeProduct(productId, user);
    }

    @GetMapping("/like")
    public List<Product> getLikedProducts(@AuthenticationPrincipal User user) {
        log.info("{}", user);

        return productService.getFavoriteProducts(user);
    }

}
