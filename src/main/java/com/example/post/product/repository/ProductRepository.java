package com.example.post.product.repository;

import com.example.post.member.dto.DetailDto;
import com.example.post.member.dto.ListDto;
import com.example.post.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long>{
    Optional<Product> findByName(String name);

//    @Query("""
//select new com.example.post.product.dto.DetailDto(
//        p.id,
//        p.name,
//        p.category,
//        p.price,
//        p.stockQuantity,
//        p.image_path
//    )
//    from Product p
//    """)
//    Optional<DetailDto> findByDtailDto(@Param("id")Long id);

}
