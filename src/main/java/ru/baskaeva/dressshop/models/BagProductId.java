package ru.baskaeva.dressshop.models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BagProductId implements Serializable {
    private static final long serialVersionUID = -6892095636873170192L;
    private Long bagId;
    private Long productId;
}
