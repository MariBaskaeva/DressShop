package ru.baskaeva.dressshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
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
    private String vendorCode;
    private String color;
    private String description;
    private String manufacturer;
    private Type type;
    private Long cost;
    private Integer count;

    // TODO: 21.04.2023 Придумать как хранить картинки
    private String image;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Favourite> usersWhoLiked = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    private List<BagProduct> bags = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!id.equals(product.id)) return false;
        if (!vendorCode.equals(product.vendorCode)) return false;
        if (!color.equals(product.color)) return false;
        if (!description.equals(product.description)) return false;
        if (!manufacturer.equals(product.manufacturer)) return false;
        if (type != product.type) return false;
        return image.equals(product.image);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + vendorCode.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + manufacturer.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + image.hashCode();
        return result;
    }

    // TODO: 21.04.2023 Сделать отдельной сущностью
    public enum Type{
        BLOUSE,
        TROUSERS,
        JACKET,
        COAT,
        DRESS,
        SKIRT
    }
}
