package ru.baskaeva.dressshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.baskaeva.dressshop.models.product.ProductInfo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {
    private String vendorCode;
    private String color;
    private List<ProductInfo.Type> size;
    private String title;
    private String description;
    private String manufacturer;
    private ProductInfo.Type type;
    private String image;
    private Long cost;
    private Boolean isFavourite;
}
