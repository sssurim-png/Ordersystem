package com.example.post.product.controller;


import com.example.post.product.dto.CreateDto;
import com.example.post.product.dto.DetailDto;
import com.example.post.product.dto.ListDto;
import com.example.post.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//        1. 상품등록
        @PostMapping("/create")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> create(@RequestPart("product") CreateDto dto, @RequestPart("productImage")MultipartFile productImage, @AuthenticationPrincipal String email){
        productService.save(dto, productImage,email);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");

        }

//        2. 상품상세조회

    @GetMapping("/detail/{id}")
    public DetailDto findproduct(@PathVariable Long id){
        return productService.findproduct(id);
    }

//        3. 상품목록조회
    @GetMapping("/list")
    public List<ListDto> list(@PageableDefault(size = 10, sort= "id", direction = Sort.Direction.DESC)Pageable pageable){
        return productService.findByAll(pageable).getContent();  //findByAll() → Page<ListDto> //getContent() → List<ListDto>
    }


    }

