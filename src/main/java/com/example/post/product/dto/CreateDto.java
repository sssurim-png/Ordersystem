package com.example.post.product.dto;

import com.example.post.member.domain.Member;
import com.example.post.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateDto {
    private String name;
    private int price;
    private String category;
    private int stockQuantity;
    private String productImage;

    public Product toEntity(Member member){
        return Product.builder()
                .member(member)
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .stockQuantity(this.stockQuantity)
                .image_path(this.productImage)
                .build();
    }

}
