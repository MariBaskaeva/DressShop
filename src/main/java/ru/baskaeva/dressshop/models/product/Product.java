package ru.baskaeva.dressshop.models.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private ProductInfo productInfo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductDetails> details;
}
