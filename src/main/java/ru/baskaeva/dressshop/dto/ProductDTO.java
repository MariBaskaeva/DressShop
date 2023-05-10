package ru.baskaeva.dressshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.baskaeva.dressshop.models.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String vendorCode;
    private String color;
    private String description;
    private String manufacturer;
    private Product.Type type;
    private String image;
    private Boolean isLiked;

    public ProductDTO(Product product, Boolean isLiked) {
        this.id = product.getId();
        this.vendorCode = product.getVendorCode();
        this.color = product.getColor();
        this.description = product.getDescription();
        this.manufacturer = product.getManufacturer();
        this.type = product.getType();
        this.image = product.getImage();
        this.isLiked = isLiked;
    }
}
