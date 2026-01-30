package com.example.post.order.repository;

import com.example.post.order.domain.Ordering_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<Ordering_Details, Long> {
}
