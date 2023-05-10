package ru.baskaeva.dressshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;
import ru.baskaeva.dressshop.dto.ProductCreateDTO;
import ru.baskaeva.dressshop.dto.ProductDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.Product;
import ru.baskaeva.dressshop.models.User;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> getProducts(String search, Integer page, Integer limit, User user);
    Product createProduct(ProductCreateDTO product);
    ProductDTO getProduct(long id, User user) throws Exception;
    void removeProduct(long id);
    Product updateProduct(long id, Product cat);
    void likeProduct(long id, User user) throws NoSuchProductException;
    void dislikeProduct(long id, User user) throws NoSuchProductException;
    List<Product> getFavoriteProducts(User user);
}