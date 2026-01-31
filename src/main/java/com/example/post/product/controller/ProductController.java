package com.example.post.product.controller;


import com.example.post.product.dto.CreateDto;
import com.example.post.product.dto.DetailDto;
import com.example.post.product.dto.SearchDto;
import com.example.post.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        public ResponseEntity<?> create(@ModelAttribute CreateDto createDto){
        Long id = productService.save(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);

        }

//        2. 상품상세조회

    @GetMapping("/detail/{id}")
    public DetailDto findproduct(@PathVariable Long id){
        return productService.findproduct(id);
    }

//        3. 상품목록조회
//    @GetMapping("/list")
//    public List<ListDto> list(Pageable pageable,@ModelAttribute ProductSearchDto searchDto){//검색은안넣었다 Seatch Dto 생성하기 // 변수 겹쳐서 디테일,리스트 같이 써도 된다
//        return productService.findByAll(pageable).getContent();  //findByAll() → Page<ListDto> //getContent() → List<ListDto>
//    } //paging처리만

    @GetMapping("/list")
    public ResponseEntity<?>findAll(Pageable pageable, SearchDto searchDto){
        Page<SearchDto> prodResDto = productService.findByAll(pageable,searchDto);
        return ResponseEntity.status(HttpStatus.OK).body(prodResDto);
    }


    }

