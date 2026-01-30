package com.example.post.order.dto;

import com.example.post.order.domain.Ordering_Details;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MyOrders { //member+order+detail
    private Long id;
    private String email;
    private Order order;
    private List<Ordering_Details> orderingDetails;

}
