package ru.baskaeva.dressshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductColorDTO {
    private Long id;
    private String color;
    private String image;
    private Long price;
    private Boolean isLiked;
    private List<ProductSizeDTO> sizes;
}
