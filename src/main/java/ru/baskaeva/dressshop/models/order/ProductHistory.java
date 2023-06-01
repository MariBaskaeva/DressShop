package ru.baskaeva.dressshop.models.order;

import jakarta.persistence.*;
import lombok.*;
import ru.baskaeva.dressshop.models.product.ProductInfo;
import ru.baskaeva.dressshop.models.product.ProductSize;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private ProductInfo productInfo;

    private String color;
    private String image;
    private Long price;
    private ProductSize.Size size;
    private Integer count;
}

