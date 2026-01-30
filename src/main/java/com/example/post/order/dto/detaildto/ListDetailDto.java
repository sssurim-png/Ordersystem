package com.example.post.order.dto.detaildto;

import com.example.post.order.domain.Ordering_Details;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ListDetailDto {
    private Long id;
    private String name;
    private int quantity;

//    public Ordering_Details toEntity(){
//        return Ordering_Details.builder()
//                .id(this.getId())
//                .
//
//                .build();
//    }
}
