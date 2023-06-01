package ru.baskaeva.dressshop.models.bag;

import jakarta.persistence.*;
import lombok.*;
import ru.baskaeva.dressshop.models.user.User;
import ru.baskaeva.dressshop.models.product.ProductSize;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BagProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "productSize_id")
    private ProductSize productSize;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private User user;

    private Integer count;

}