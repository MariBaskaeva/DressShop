package ru.baskaeva.dressshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.baskaeva.dressshop.models.product.ProductSize;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeDTO {
    private Long id;
    private ProductSize.Size size;
    private Integer count;
    private Integer countInBag;
}
