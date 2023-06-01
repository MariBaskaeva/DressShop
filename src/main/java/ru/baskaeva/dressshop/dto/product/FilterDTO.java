package ru.baskaeva.dressshop.dto.product;

import lombok.Builder;
import lombok.Data;
import ru.baskaeva.dressshop.models.product.ProductInfo;
import ru.baskaeva.dressshop.models.product.ProductSize;

import java.util.List;

@Data
@Builder
public class FilterDTO {
    private List<String> colors;
    private List<ProductInfo.Type> types;
    private List<ProductSize.Size> sizes;
    private Long priceFrom;
    private Long priceTo;
    private Boolean isLiked;
}
