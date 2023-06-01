package ru.baskaeva.dressshop.services.interfaces;

import org.springframework.data.domain.Page;
import ru.baskaeva.dressshop.dto.product.FilterDTO;
import ru.baskaeva.dressshop.dto.product.ProductCreateDTO;
import ru.baskaeva.dressshop.dto.product.ProductDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.product.Product;
import ru.baskaeva.dressshop.models.user.User;

public interface IProductService {
    Page<ProductDTO> getProducts(Integer page, Integer limit, FilterDTO filterDTO, User user);
    Product createProduct(ProductCreateDTO product);
    ProductDTO getProduct(long id, User user) throws Exception;
    void removeProduct(long id);
    Product updateProduct(long id, Product cat);
    void likeProduct(long id, User user) throws NoSuchProductException;
    void dislikeProduct(long id, User user) throws NoSuchProductException;
    Page<ProductDTO> getFavoriteProducts(User user, Integer page, Integer limit);
}