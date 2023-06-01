package ru.baskaeva.dressshop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.baskaeva.dressshop.dto.product.*;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.bag.BagProduct;
import ru.baskaeva.dressshop.models.favourite.Favourite;
import ru.baskaeva.dressshop.models.product.Product;
import ru.baskaeva.dressshop.models.product.ProductDetails;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.repositories.favourite.FavouriteRepository;
import ru.baskaeva.dressshop.repositories.product.ProductRepository;
import ru.baskaeva.dressshop.repositories.product.ProductDetailsRepository;
import ru.baskaeva.dressshop.repositories.product.ProductSizeRepository;
import ru.baskaeva.dressshop.services.interfaces.IProductService;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final FavouriteRepository favouriteRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductSizeRepository productSizeRepository;
    private final BagService bagService;

    @Override
    public Page<ProductDTO> getProducts(Integer page, Integer limit, FilterDTO filterDTO, User user) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, limit));
        log.info("{}", products);
        return filterProducts(products, filterDTO, user);
    }

    private Page<ProductDTO> filterProducts(Page<Product> page, FilterDTO filterDTO, User user) {
        List<ProductDTO> list = page.getContent().stream().map((p) -> convertToDTO(p, user)).toList();
        list = list.stream().filter(p -> filterFields(p, filterDTO)).toList();
        if (filterDTO.getPriceTo() != null)
            list.forEach(p -> p.setColors(p.getColors().stream().filter(d -> d.getPrice() <= filterDTO.getPriceTo()).toList()));
        if (filterDTO.getPriceFrom() != null)
            list.forEach(p -> p.setColors(p.getColors().stream().filter(d -> d.getPrice() >= filterDTO.getPriceFrom()).toList()));
        if (filterDTO.getIsLiked() != null)
            list.forEach(p -> p.setColors(p.getColors().stream().filter(d -> d.getIsLiked().equals(filterDTO.getIsLiked())).toList()));
        if (filterDTO.getSizes() != null)
            list.forEach(p -> p.getColors().forEach(d -> d.setSizes(d.getSizes().stream().filter(s -> filterDTO.getSizes().contains(s.getSize())).toList())));
        list.forEach(p -> p.setColors(p.getColors().stream().filter(d -> !d.getSizes().isEmpty()).toList()));
        list = list.stream().filter(p -> !p.getColors().isEmpty()).toList();
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }
    
    private ProductDTO convertToDTO(Product product, User user) {
        boolean isLiked = product.getDetails() != null && !product.getDetails().isEmpty() && isLike(product.getDetails().get(0).getId(), user);
        ProductDTO.ProductDTOBuilder builder = ProductDTO.builder()
                .id(product.getId())
                .info(product.getProductInfo())
                .colors(product.getDetails().stream().map(d -> 
                        ProductColorDTO.builder()
                                .id(d.getId())
                                .color(d.getColor())
                                .image(d.getImage())
                                .price(d.getPrice())
                                .isLiked(isLiked)
                                .sizes(d.getSizes().stream().map(s ->
                                                ProductSizeDTO.builder()
                                                        .id(s.getId())
                                                        .size(s.getSize())
                                                        .count(s.getCount())
                                                        .countInBag(bagService.countInBag(s.getId(), user))
                                                        .build()
                                        ).toList())
                                .build()
                        ).toList());
                
        return builder.build();
    }

    private boolean filterFields(ProductDTO product, FilterDTO filter) {
        boolean flag = true;
        if (filter.getColors() != null) {
            flag = filter.getColors().stream().anyMatch(c -> product.getColors().stream().anyMatch(p -> p.getColor().equals(c)));
        }
        if (filter.getTypes() != null) {
            flag = flag && filter.getTypes().contains(product.getInfo().getType());
        }
        return flag;
    }
    @Override
    public Product createProduct(ProductCreateDTO product) {
//        List<Size> sizes = sizeRepository.findAll();
//        return productRepository.save(Product.builder()
//                        .vendorCode(product.getVendorCode())
//                        .color(product.getColor())
//                        .size(sizes.stream().filter(s -> product.getSize().contains(s.getType())).toList())
//                        .description(product.getDescription())
//                        .manufacturer(product.getManufacturer())
//                        .type(product.getType())
//                        .image(product.getImage())
//                .build());
        return null;
    }

    @Override
    public ProductDTO getProduct(long id, User user) throws NoSuchProductException {
        Product product = productRepository.getProductById(id).orElseThrow(() -> new NoSuchProductException("Product with id = " + id + " not found"));
        return convertToDTO(product, user);
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

    public boolean isLike(long id, User user ) {
        return favouriteRepository.existsByProductDetails_IdAndUser(id, user);
    }

    @Override
    @Transactional
    public void likeProduct(long id, User user) throws NoSuchProductException {
        ProductDetails productDetails = productDetailsRepository.getById(id).orElseThrow(() -> new NoSuchProductException("Product with id = " + id + " not found!"));
        for (ProductDetails pd : productDetails.getProduct().getDetails()) {
            Favourite favourite = Favourite.builder()
                    .productDetails(pd)
                    .user(user)
                    .build();
            favouriteRepository.save(favourite);
        }
    }

    @Override
    @Transactional
    public void dislikeProduct(long id, User user) throws NoSuchProductException {
        ProductDetails productDetails = productDetailsRepository.getById(id).orElseThrow(() -> new NoSuchProductException("Product with id = " + id + " not found!"));
        for (ProductDetails pd : productDetails.getProduct().getDetails()) {
            favouriteRepository.deleteByProductDetails_IdAndUser(pd.getId(), user);
        }
    }

    @Override
    public Page<ProductDTO> getFavoriteProducts(User user, Integer page, Integer limit) {
//        Page<Product> products = favouriteRepository.findProductByUser(user, PageRequest.of(page, limit));
//
//        return products.map(product -> new ProductDTO(product, product.getUsersWhoLiked().stream().filter(fav -> fav.getUser().equals(user)).count() == 1));
        return null;
    }
}
