package ru.baskaeva.dressshop.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteId implements Serializable {
    private static final long serialVersionUID = -8027572913620112034L;
    private Long userId;
    private Long productId;
}
