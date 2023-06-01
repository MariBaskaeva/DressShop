package ru.baskaeva.dressshop.models.product;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductInfo {
    private String title;
    private String vendorCode;
    private String description;
    private String manufacturer;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    public enum Type{
        BLOUSE,
        TROUSERS,
        JACKET,
        COAT,
        DRESS,
        SKIRT
    }
}
