package ru.baskaeva.dressshop.repositories.favourite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.baskaeva.dressshop.models.favourite.Favourite;
import ru.baskaeva.dressshop.models.user.User;

public interface FavouriteRepository extends PagingAndSortingRepository<Favourite, Long> {
    Page<Favourite> getAllByUser(User user, Pageable pageable);
    boolean existsByProductDetails_IdAndUser(Long id, User user);
    Favourite save(Favourite favourite);
    void deleteByProductDetails_IdAndUser(Long id, User user);
}
