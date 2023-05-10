package ru.baskaeva.dressshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.baskaeva.dressshop.dto.ProductCreateDTO;
import ru.baskaeva.dressshop.dto.ProductDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.Favourite;
import ru.baskaeva.dressshop.models.FavouriteId;
import ru.baskaeva.dressshop.models.Product;
import ru.baskaeva.dressshop.models.User;
import ru.baskaeva.dressshop.repositories.FavouriteRepository;
import ru.baskaeva.dressshop.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FavouriteRepository favouriteRepository;

    @Override
    public Page<ProductDTO> getProducts(String search, Integer page, Integer limit, User user) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, search)));

        return products.map(product -> new ProductDTO(product, product.getUsersWhoLiked().stream().filter(fav -> fav.getUser().equals(user)).count() == 1));
    }

    @Override
    public Product createProduct(ProductCreateDTO product) {
        return productRepository.save(Product.builder()
                        .vendorCode(product.getVendorCode())
                        .color(product.getColor())
                        .description(product.getDescription())
                        .manufacturer(product.getManufacturer())
                        .type(product.getType())
                        .image(product.getImage())
                .build());
    }

    @Override
    public ProductDTO getProduct(long id, User user) throws NoSuchProductException {
        Product product = productRepository.getProductById(id).orElseThrow(() -> new NoSuchProductException("Product with id = " + id + " not found"));
        return new ProductDTO(product, product.getUsersWhoLiked().contains(user));
    }

    @Override
    public void removeProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(long id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public void likeProduct(long id, User user) throws NoSuchProductException {
        Product product = productRepository.getProductById(id).orElseThrow(() -> new NoSuchProductException("Product with id = " + id + " not found!"));
        Favourite favourite = Favourite.builder()
                .id(new FavouriteId(user.getId(), id))
                .product(product)
                .user(user)
                .build();
        favouriteRepository.save(favourite);
    }

    @Override
    public void dislikeProduct(long id, User user) throws NoSuchProductException {
        Product product = productRepository.getProductById(id).orElseThrow(() -> new NoSuchProductException("Product with id = " + id + " not found!"));
        favouriteRepository.deleteByProductAndUser(product, user);
    }

    @Override
    public List<Product> getFavoriteProducts(User user) {
        return favouriteRepository.findProductByUser(user);
    }
}
