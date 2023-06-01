package ru.baskaeva.dressshop.repositories.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.baskaeva.dressshop.models.product.ProductDetails;

import java.util.Optional;

@Repository
public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Long> {
    Optional<ProductDetails> getById(Long id);
}

