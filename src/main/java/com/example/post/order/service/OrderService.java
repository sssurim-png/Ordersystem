package com.example.post.order.service;

import com.example.post.order.domain.Ordering_Details;
import com.example.post.order.domain.Orders;
import com.example.post.order.dto.detaildto.OrderCreateDto;
import com.example.post.order.repository.OrderDetailRepository;
import com.example.post.order.repository.OrderRepository;
import com.example.post.product.domain.Product;
import com.example.post.product.dto.CreateDto;
import com.example.post.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class OrderService {
    private OrderDetailRepository orderDetailRepository;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
@Autowired
    public OrderService(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
}

//    1. 주문하기

    public void save(OrderCreateDto dto){
        Product product = productRepository.findById(dto.getProduct().getId()).orElseThrow(()-> new NoSuchElementException("없는 상품입니다"));//  //product id가 있는지 확인 후 주문

        Ordering_Details orderingDetails = OrderCreateDto.toEntity(product);
        List<Orders> ordesList = new ArrayList<>();
        ordesList.add(orderingDetails);
        orderRepository.save(orderList);

//        detail주문 확보 후 Order에 add해서 Order로 반환//List갖다박기


//    detail애들 통합해서 order로 save

    }



//    2. 주문목록 조회

//    3. 내주문목록조회


}
