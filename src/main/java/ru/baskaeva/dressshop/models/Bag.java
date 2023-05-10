package ru.baskaeva.dressshop.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Bag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany(
            mappedBy = "bag",
            cascade = CascadeType.ALL
    )
    private List<BagProduct> products = new ArrayList<>();

    public void addProduct(Product product) {
        Optional<BagProduct> opt = products.stream().filter(x -> x.getProduct().equals(product)).findFirst();
        if (opt.isPresent()) {
            BagProduct bagProduct = opt.get();
            bagProduct.setCount(bagProduct.getCount() + 1);
        } else {
            BagProduct bagProduct = BagProduct.builder()
                    .id(BagProductId.builder().bagId(id).productId(product.getId()).build())
                    .bag(this)
                    .product(product)
                    .count(1)
                    .build();
            products.add(bagProduct);
        }
    }

    public void removeProduct(Product product) {
        Optional<BagProduct> opt = products.stream().filter(x -> x.getProduct().equals(product)).findFirst();
        if (opt.isPresent()) {
            BagProduct bagProduct = opt.get();
            bagProduct.setCount(bagProduct.getCount() - 1);
            if (bagProduct.getCount() == 0) {
                products.remove(bagProduct);
            }
        }
    }

    public void clear() {
        products.clear();
    }
}