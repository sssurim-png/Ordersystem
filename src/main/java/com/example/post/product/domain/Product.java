package com.example.post.product.domain;

import com.example.post.common.domain.DateTime;
import com.example.post.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@ToString
@Builder
public class Product extends DateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    private int price;
    @Column
    private String category;
    private int stockQuantity;
    private String image_path;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),nullable = false)
    private Member member;

    @Builder
    public Product(Member member,
                   String name,
                   int price,
                   String category,
                   int stockQuantity,
                   String image_path) {

        this.member = member;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.image_path = image_path;
    }

    public void updatefileNameUrl(String productImage){
        this.image_path = productImage;
    }

}
