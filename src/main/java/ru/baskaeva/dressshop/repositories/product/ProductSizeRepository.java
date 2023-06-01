package ru.baskaeva.dressshop.repositories.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.baskaeva.dressshop.models.product.ProductSize;

import java.util.Optional;

@Repository
public interface ProductSizeRepository extends CrudRepository<ProductSize, Long> {
    Optional<ProductSize> findById(Long id);
}
