package ru.baskaeva.dressshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.baskaeva.dressshop.models.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {
    private String vendorCode;
    private String color;
    private String description;
    private String manufacturer;
    private Product.Type type;
    private String image;
    private Boolean isFavourite;
}
