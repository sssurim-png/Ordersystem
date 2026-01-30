package com.example.post.product.dto;

import com.example.post.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.print.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ListDto { //페이징 처리, 사이즈?
    private String name;
    private String category;

    public static ListDto fromEntity(Product product){
        return ListDto.builder()
                .name(product.getName())
                .category(product.getCategory())
                .build();
    }

}
