package com.example.post.order.dto;

import com.example.post.order.domain.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetailDto { //detail쪽
    private Long id;
    private String productName;
    private int productCount;

    public static OrderDetailDto fromEntity(OrderDetail orderDetail){
        return OrderDetailDto.builder()
                .id(orderDetail.getId())
                .productName (orderDetail.getProduct().getName())
                .productCount(orderDetail.getQuantity())
                .build();


    }
}
