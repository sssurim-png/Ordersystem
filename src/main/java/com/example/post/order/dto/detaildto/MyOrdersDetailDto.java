package com.example.post.order.dto.detaildto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MyOrdersDetailDto { //detail쪽
    private Long id;
    private String name;
    private int count;
}
