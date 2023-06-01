package ru.baskaeva.dressshop.models.favourite;

import jakarta.persistence.*;
import lombok.*;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.models.product.ProductDetails;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Favourite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ProductDetails productDetails;

    @ManyToOne
    private User user;
}
