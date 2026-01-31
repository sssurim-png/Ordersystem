package com.example.post.product.service;

import com.example.post.member.domain.Member;
import com.example.post.member.repository.MemberRepository;
import com.example.post.product.domain.Product;
import com.example.post.product.dto.CreateDto;
import com.example.post.product.dto.DetailDto;
import com.example.post.product.dto.SearchDto;
import com.example.post.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket1}")
    private String bucket;

    public ProductService(ProductRepository productRepository, MemberRepository memberRepository, S3Client s3Client) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.s3Client = s3Client;
    }


    //    1. 상품등록
    public Long save(CreateDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("회원 없음"));
        Product product = productRepository.save(dto.toEntity(member));

//        파일업로드
        if (dto.getProductImage() != null) {
            String fileName = "product-" + product.getId() + "-productImage-" + dto.getProductImage().getOriginalFilename();
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(dto.getProductImage().getContentType())
                    .build();

            try {
                s3Client.putObject(request, RequestBody.fromBytes(dto.getProductImage().getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String imgUrl = s3Client.utilities().getUrl(a -> a.bucket(bucket).key(fileName)).toExternalForm();
            product.updatefileNameUrl(imgUrl);//뒤늦게 변경
        }
        return product.getId();
    }

//    2. 상품상세조회
    public DetailDto findproduct(Long id) {
        Optional<Product> opt_products = productRepository.findById(id);
        Product product = opt_products.orElseThrow(()->new NoSuchElementException("상품아이디가 없습니다"));
        DetailDto dto = DetailDto.fromEntity(product);
        return dto;
    }

//    3. 상품목록조회
    public Page<SearchDto> findByAll(Pageable pageable, SearchDto searchDto) {//검색도 하기
        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (searchDto.getName() != null) {
                    predicateList.add(criteriaBuilder.like(root.get("name"), "%" + searchDto.getName() + "%"));
                }
                if (searchDto.getCategory() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("category"), searchDto.getCategory()));
                }
                Predicate[] predicateArr = new Predicate[predicateList.size()];
                for (int i = 0; i < predicateArr.length; i++) {
                    predicateArr[i] = predicateList.get(i);
                }
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
                }
            };

            Page<Product> postList = productRepository.findAll(specification, pageable);
        return postList.map(p->searchDto.fromEntity(p));
        }
    }






