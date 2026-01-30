package com.example.post.order.dto;

import com.example.post.order.domain.Ordering_Details;
import com.example.post.order.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ListDto {
    private Long id;
    private Status ORDERED;
    private List<Ordering_Details> orderingDetails;
}
