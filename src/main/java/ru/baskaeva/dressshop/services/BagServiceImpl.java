package ru.baskaeva.dressshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.*;
import ru.baskaeva.dressshop.repositories.BagRepository;
import ru.baskaeva.dressshop.repositories.ProductRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BagServiceImpl implements BagService {
    private final BagRepository repository;
    private final ProductRepository productRepository;

    @Override
    public Bag getBag(User user) {
        return repository.findByUser(user).orElseThrow(RuntimeException::new);
    }

    @Override
    public Bag addProductToBag(User user, Long id) throws NoSuchProductException {
        Bag bag = repository.findByUser(user).orElseThrow(RuntimeException::new);
        Product product = productRepository.getProductById(id).orElseThrow(() -> new NoSuchProductException("Product with id = " + id + " not found"));

        bag.addProduct(product);

        return repository.save(bag);
    }

    @Override
    public Bag deleteFromBag(Long id, User user) throws NoSuchProductException {
        Bag bag = repository.findByUser(user).orElseThrow(RuntimeException::new);
        Product product = productRepository.getProductById(id).orElseThrow(() -> new NoSuchProductException("Product with id = " + id + " not found"));

        bag.removeProduct(product);

        return repository.save(bag);
    }

    @Override
    public Bag clearBag(User user) {
        Bag bag = repository.findByUser(user).orElseThrow(RuntimeException::new);

        bag.clear();

        return repository.update(new HashSet<>(), bag.getId());
    }
}
