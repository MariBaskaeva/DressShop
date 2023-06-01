package ru.baskaeva.dressshop.services.interfaces;

import ru.baskaeva.dressshop.dto.bag.BagProductDTO;
import ru.baskaeva.dressshop.dto.product.ProductDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.bag.BagProduct;
import ru.baskaeva.dressshop.models.user.User;

import java.util.List;

public interface IBagService {
    List<BagProductDTO> getBag(User user);
    BagProduct addProductToBag(User user, Long id) throws NoSuchProductException;
    void deleteFromBag(Long id, User user) throws NoSuchProductException;
    void clearBag(User user);
    int countInBag(Long id, User user);
}
