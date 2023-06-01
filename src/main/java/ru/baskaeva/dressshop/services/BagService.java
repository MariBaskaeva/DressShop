package ru.baskaeva.dressshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.baskaeva.dressshop.dto.bag.BagProductDTO;
import ru.baskaeva.dressshop.dto.product.ProductDTO;
import ru.baskaeva.dressshop.exceptions.NoSuchProductException;
import ru.baskaeva.dressshop.models.bag.BagProduct;
import ru.baskaeva.dressshop.models.product.Product;
import ru.baskaeva.dressshop.models.product.ProductDetails;
import ru.baskaeva.dressshop.models.product.ProductInfo;
import ru.baskaeva.dressshop.models.product.ProductSize;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.repositories.bag.BagProductRepository;
import ru.baskaeva.dressshop.repositories.product.ProductRepository;
import ru.baskaeva.dressshop.repositories.product.ProductSizeRepository;
import ru.baskaeva.dressshop.services.interfaces.IBagService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BagService implements IBagService {
    private final BagProductRepository bagProductRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository productSizeRepository;

    @Override
    public List<BagProductDTO> getBag(User user) {
        List<BagProduct> bagProducts = bagProductRepository.getByUser(user);
        return bagProducts.stream().map(this::convertToDTO).toList();
    }

    private BagProductDTO convertToDTO(BagProduct bagProduct) {
        var productSize = bagProduct.getProductSize();
        var details = productSize.getDetails();
        return BagProductDTO.builder()
                .bagId(bagProduct.getId())
                .productId(productSize.getId())
                .title(details.getProduct().getProductInfo().getTitle())
                .type(details.getProduct().getProductInfo().getType())
                .color(details.getColor())
                .image(details.getImage())
                .price(details.getPrice())
                .size(productSize.getSize())
                .count(productSize.getCount())
                .countInBag(bagProduct.getCount())
                .build();
    }

    @Override
    public BagProduct addProductToBag(User user, Long sizeId) throws NoSuchProductException {
        ProductSize productSize = productSizeRepository.findById(sizeId).orElseThrow(NoSuchProductException::new);
        BagProduct bagProduct = bagProductRepository.getByProductSize(productSize).orElse(BagProduct.builder()
                .user(user)
                .count(0)
                .productSize(productSize)
                .build());

        if(bagProduct.getCount() + 1 > productSize.getCount())
            // TODO: 13.05.2023 исправить исключение
            throw new RuntimeException();

        bagProduct.setCount(bagProduct.getCount() + 1);
        return bagProductRepository.save(bagProduct);
    }

    @Override
    public void deleteFromBag(Long sizeId, User user) throws NoSuchProductException {
        ProductSize productSize = productSizeRepository.findById(sizeId).orElseThrow(NoSuchProductException::new);
        BagProduct bagProduct = bagProductRepository.getByProductSize(productSize).orElse(BagProduct.builder()
                .user(user)
                .count(0)
                .productSize(productSize)
                .build());

        if(bagProduct.getCount() > 1){
            bagProduct.setCount(bagProduct.getCount() - 1);
            bagProductRepository.save(bagProduct);
        }
        else{
            bagProductRepository.delete(bagProduct);
        }
    }

    @Override
    @Transactional
    public void clearBag(User user) {
        bagProductRepository.deleteByUser(user);
    }

    @Override
    public int countInBag(Long id, User user) {
        Optional<BagProduct> bagProduct = bagProductRepository.getByProductSize_idAndUser(id, user);
        if (bagProduct.isEmpty())
            return 0;
        return bagProduct.get().getCount();
    }
}
