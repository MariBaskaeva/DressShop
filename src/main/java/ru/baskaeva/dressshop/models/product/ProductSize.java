package ru.baskaeva.dressshop.models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.baskaeva.dressshop.models.bag.BagProduct;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Size size;

    private Integer count;

    @JsonIgnore
    @ManyToOne
    private ProductDetails details;

    public enum Size {
        XS,
        S,
        M,
        L,
        XL,
        XXL
    }
}

