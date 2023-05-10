package ru.baskaeva.dressshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.baskaeva.dressshop.dto.ProductDTO;
import ru.baskaeva.dressshop.models.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Product save(Product product);
    Optional<Product> getProductById(long id);
    void deleteById(long id);
    @Query("SELECT u.products from User u where u.id = :id")
    List<Product> getAllByUser(Long id);

    Page<ProductDTO> findAll(Specification<Product> spec);
}
