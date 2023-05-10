package ru.baskaeva.dressshop.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.baskaeva.dressshop.models.Bag;
import ru.baskaeva.dressshop.models.BagProduct;
import ru.baskaeva.dressshop.models.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BagRepository extends CrudRepository<Bag, Long> {
    Optional<Bag> findByUser(User user);
    @Query("update Bag b set b.products = :products where b.id = :id")
    Bag update(Set<BagProduct> products, Long id);
}
