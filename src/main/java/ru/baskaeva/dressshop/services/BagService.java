package ru.baskaeva.dressshop.services;

import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.Bag;
import ru.baskaeva.dressshop.models.User;

public interface BagService {
    Bag getBag(User user);
    Bag addProductToBag(User user, Long id) throws NoSuchProductException;
    Bag deleteFromBag(Long id, User user) throws NoSuchProductException;
    Bag clearBag(User user);
}
