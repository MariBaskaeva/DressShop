package ru.baskaeva.dressshop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.baskaeva.dressshop.dto.product.FilterDTO;
import ru.baskaeva.dressshop.dto.product.ProductCreateDTO;
import ru.baskaeva.dressshop.dto.product.ProductDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.product.Product;
import ru.baskaeva.dressshop.models.product.ProductInfo;
import ru.baskaeva.dressshop.models.product.ProductSize;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.services.interfaces.IProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.*;



@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping("/products")
    public Page<ProductDTO> getAllProducts(@RequestParam Integer page, @RequestParam Integer limit,
                                           @RequestParam(required = false) String colors,
                                           @RequestParam(required = false) String types,
                                           @RequestParam(required = false) String sizes,
                                           @RequestParam(required = false) Long priceFrom,
                                           @RequestParam(required = false) Long priceTo,
                                           @RequestParam(required = false) Boolean isLiked,
                                           @AuthenticationPrincipal User user) {
        FilterDTO filterDTO = FilterDTO.builder()
                .colors(colors == null ? null : Arrays.stream(colors.split(",")).toList())
                .types(types == null ? null : Arrays.stream(types.split(",")).map(ProductInfo.Type::valueOf).toList())
                .sizes(sizes == null ? null : Arrays.stream(sizes.split(",")).map(ProductSize.Size::valueOf).toList())
                .priceFrom(priceFrom)
                .priceTo(priceTo)
                .isLiked(isLiked)
                .build();
        log.info("filter {}", filterDTO);
        return productService.getProducts(page, limit, filterDTO, user);
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDTO postProduct(@RequestBody ProductCreateDTO product) {
        log.info("{}", product);
        return new ProductDTO();
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

    // TODO: 19.05.2023 Сделать
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
    public Page<ProductDTO> getLikedProducts(@AuthenticationPrincipal User user, @RequestParam Integer page,
                                             @RequestParam Integer limit) {
        log.info("{}", user);

        return productService.getFavoriteProducts(user, page, limit);
    }

}
