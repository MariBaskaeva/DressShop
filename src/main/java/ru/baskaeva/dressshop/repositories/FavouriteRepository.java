package ru.baskaeva.dressshop.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.baskaeva.dressshop.models.Favourite;
import ru.baskaeva.dressshop.models.FavouriteId;
import ru.baskaeva.dressshop.models.Product;
import ru.baskaeva.dressshop.models.User;

import java.util.List;

public interface FavouriteRepository extends CrudRepository<Favourite, FavouriteId> {
    @Query("SELECT f.product FROM Favourite f WHERE f.user=:user")
    List<Product> findProductByUser(User user);
    void deleteByProductAndUser(Product product, User user);
}
