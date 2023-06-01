package ru.baskaeva.dressshop.repositories.bag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.baskaeva.dressshop.models.bag.BagProduct;
import ru.baskaeva.dressshop.models.product.ProductSize;
import ru.baskaeva.dressshop.models.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BagProductRepository extends CrudRepository<BagProduct, Long> {
    Optional<BagProduct> getByProductSize(ProductSize size);
    Optional<BagProduct> getByProductSize_idAndUser(Long id, User user);
    List<BagProduct> getByUser(User user);
    void deleteByUser(User user);

}
