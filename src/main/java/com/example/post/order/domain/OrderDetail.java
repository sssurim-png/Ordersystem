package com.example.post.order.domain;

import com.example.post.common.domain.DateTime;
import com.example.post.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class OrderDetail extends DateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordering_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),nullable = false)
    private Ordering ordering;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),nullable = false)
    private Product product;


}
