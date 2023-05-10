package ru.baskaeva.dressshop.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BagProduct {
    @EmbeddedId
    private BagProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bagId")
    private Bag bag;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    private Integer count;
}