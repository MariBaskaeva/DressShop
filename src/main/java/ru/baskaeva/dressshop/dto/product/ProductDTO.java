package ru.baskaeva.dressshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.baskaeva.dressshop.models.product.ProductInfo;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private ProductInfo info;
    private List<ProductColorDTO> colors;
}
