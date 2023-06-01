package ru.baskaeva.dressshop.models.order;

import jakarta.persistence.*;
import lombok.*;
import ru.baskaeva.dressshop.models.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long timestamp;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductHistory> products;

    private String deliveryAddress;
    private LocalDateTime dateTime;
    private Long totalCost;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    private User user;

    public enum Status {
        AwaitingPayment,
        InProgress,
        Cancelled,
        Delivered
    }
}
