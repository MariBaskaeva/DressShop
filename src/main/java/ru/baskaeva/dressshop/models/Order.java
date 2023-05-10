package ru.baskaeva.dressshop.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfDelivery;
    private Long totalCost;
    @OneToMany
    private List<ProductHistory> products;
    @ManyToOne
    private User user;
    private Status status;

    public enum Status{
        InProgress,
        Delivering,
        Delivered,
        Canceled
    }

    public void addProduct(ProductHistory history) {
        products.add(history);
    }
}
