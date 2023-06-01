package ru.baskaeva.dressshop.models.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String color;
    private String image;
    private Long price;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "details")
    private List<ProductSize> sizes = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    private Product product;
}
