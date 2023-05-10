package ru.baskaeva.dressshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String vendorCode;
    private String color;
    private String description;
    private String manufacturer;
    private String type;
    private Long cost;
    private Integer count;
    private String image;

    @ManyToOne
    private Order assignOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductHistory product = (ProductHistory) o;

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

    public static ProductHistory fromProduct(Product product) {
        return ProductHistory.builder()
                .color(product.getColor())
                .cost(product.getCost())
                .count(1)
                .description(product.getDescription())
                .manufacturer(product.getManufacturer())
                .vendorCode(product.getVendorCode())
                .type(product.getType().name())
                .image(product.getImage())
                .build();
    }
}
