package ru.baskaeva.dressshop.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.baskaeva.dressshop.models.ProductHistory;

public interface ProductHistoryRepository extends CrudRepository<ProductHistory, Long> {
}
