package com.example.post.product.repository;

import com.example.post.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long>{
    Optional<Product> findByName(String name);
    //검색처리하려면 findAll 설정다시하기

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
Page<Product> findAll(Specification<Product> specification, Pageable pageable);
}
