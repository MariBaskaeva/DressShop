package ru.baskaeva.dressshop.repositories.product;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.baskaeva.dressshop.models.product.Product;
import ru.baskaeva.dressshop.models.user.User;

import java.util.Optional;


@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Optional<Product> getProductById(Long id);
    void deleteById(Long id);
    Product save(Product product);
}