package com.example.post.order.service;

import com.example.post.member.domain.Member;
import com.example.post.member.repository.MemberRepository;
import com.example.post.order.domain.OrderDetail;
import com.example.post.order.domain.Ordering;
import com.example.post.order.dto.OrderCreateDto;
import com.example.post.order.dto.OrderListDto;
import com.example.post.order.repository.OrderDetailRepository;
import com.example.post.order.repository.OrderRepository;
import com.example.post.product.domain.Product;
import com.example.post.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderingService {
    private OrderRepository orderRepository;
    private MemberRepository memberRepository;
    private ProductRepository productRepository;


    @Autowired
    public OrderingService( OrderRepository orderRepository, ProductRepository productRepository, MemberRepository memberRepository, OrderRepository orderRepository1, OrderDetailRepository orderDetailRepository1, MemberRepository memberRepository1, ProductRepository productRepository1) {

        this.orderRepository = orderRepository1;
        this.memberRepository = memberRepository1;
        this.productRepository = productRepository1;
    }

    //    1. 주문하기
    public Long save(List<OrderCreateDto> orderCreateDtoList) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Member member =memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("entity is not found"));
        Ordering ordering =Ordering.builder()
                .member(member)
                .build();//1. 주문에는 회원이 필연적 //회원값넣기
        for(OrderCreateDto dto: orderCreateDtoList){ //dto= 들어가는 형식 정의
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(()->new EntityNotFoundException("없는 물건"));
            OrderDetail orderDetail = OrderDetail.builder() //2. 주문상세
                    .ordering(ordering)
                    .product(product)
                    .quantity(dto.getProductCount())
                    .build();
            ordering.getOrderDetailList().add(orderDetail); //이거는 주문에서 상세 주문 조회할 때//상세내용 안들어가봐도 되면 안넣어도 됌/ cascade해서 한번에 저장하려고. 안하면 따로따로 각자 저장
        }
        orderRepository.save(ordering);

        //detail주문 확보 후 Order에 add해서 Order로 반환//List갖다박기
        // 각각의 물건한건(createDto-각각의 물건/수량)을 한 주문으로 묶어서 order에 넣어라
    return ordering.getId();
    }

//    detail애들 통합해서 order로 save
//        1.cascading안하면 repo에 주입 2.cascading해서 repo X(없어도 된다)



//    2. 주문목록 조회
    public List<OrderListDto>findByAll(){
        List<OrderListDto> listDtos =new ArrayList<>();
        List<Ordering> orderings = orderRepository.findAll();
        for(Ordering ordering : orderings){
            listDtos.add(OrderListDto.fromEntity(ordering));
        }
        return listDtos;

    }


//    3. 내주문목록조회
    public List<OrderListDto>myorders(){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Member member =memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("없는 사용자 입니다"));

        List<OrderListDto> listDtos = new ArrayList<>();
        List<Ordering> orderings = orderRepository.findAllByMember(member);
        for(Ordering ordering: orderings){
            listDtos.add(OrderListDto.fromEntity(ordering));
        }

        return listDtos;

    }




}
