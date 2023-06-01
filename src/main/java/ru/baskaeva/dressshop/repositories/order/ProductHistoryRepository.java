package ru.baskaeva.dressshop.repositories.order;


import org.springframework.data.repository.CrudRepository;
import ru.baskaeva.dressshop.models.order.ProductHistory;

public interface ProductHistoryRepository extends CrudRepository<ProductHistory, Long> {
}
