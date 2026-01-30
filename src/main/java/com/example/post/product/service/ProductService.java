package com.example.post.product.service;

import com.example.post.member.domain.Member;
import com.example.post.member.repository.MemberRepository;
import com.example.post.product.domain.Product;
import com.example.post.product.dto.CreateDto;
import com.example.post.product.dto.DetailDto;
import com.example.post.product.dto.ListDto;
import com.example.post.product.repository.ProductRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
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
    public void save(CreateDto dto, MultipartFile productImage, String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        Product product = dto.toEntity(member);

        productRepository.save(product);

//        파일업로드
        if (productImage != null && !productImage.isEmpty()) {
            String fileName = "product-" + product.getId() + "-productImage-" + productImage.getOriginalFilename();
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(productImage.getContentType())
                    .build();

            try {
                s3Client.putObject(request, RequestBody.fromBytes(productImage.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String imgUrl = s3Client.utilities().getUrl(a -> a.bucket(bucket).key(fileName)).toExternalForm();
            product.updatefileNameUrl(imgUrl);

        }
    }

//    2. 상품상세조회
    public DetailDto findproduct(Long id) {
        Optional<Product> opt_products = productRepository.findById(id);
        Product product = opt_products.orElseThrow(()->new NoSuchElementException("상품아이디가 없습니다"));
        DetailDto dto = DetailDto.fromEntity(product);
        return dto;
    }
//    3. 상품목록조회
    public Page<ListDto> findByAll(Pageable pageable){
        Page<Product> page = productRepository.findAll(pageable);
        Page<ListDto>dtopage = page.map(ListDto::fromEntity);
        return dtopage;

        }

    }


