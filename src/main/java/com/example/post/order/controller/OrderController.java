package com.example.post.order.controller;

import com.example.post.member.dto.CreateDto;
import com.example.post.order.dto.detaildto.OrderCreateDto;
import com.example.post.order.service.OrderService;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordering")
public class OrderController {
    private OrderService orderService;
@Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    1. 주문하기

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody OrderCreateDto dto){
    orderService.save(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body("ok");

    }

//    2. 주문목록 조회
    @GetMapping("/list")
    public


//    3. 내주문목록조회
    @GetMapping("/myorders")
}
