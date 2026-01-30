package com.example.post.order.dto.detaildto;

import com.example.post.order.domain.Ordering_Details;
import com.example.post.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderCreateDto {

    private Product product;
    private int quantity;

    public Ordering_Details toEntity(){
        return Ordering_Details.builder()
                .product(this.product)
                .quantity(this.quantity)
                .build();

    }

}
