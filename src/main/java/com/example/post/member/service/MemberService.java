package com.example.post.member.service;

import com.example.post.member.domain.Member;
import com.example.post.member.dto.*;
import com.example.post.member.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;

    }


    //    1. 회원가입
    public void save(CreateDto dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 email입니다");
        }
        Member member = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        memberRepository.save(member);
    }


    //    2. user로그인
    public Member login(LoginDto dto) {
        Optional<Member> opt_member = memberRepository.findByEmail(dto.getEmail());
        boolean check = true;
        if (!opt_member.isPresent()) {
            check = false;
        } else {
            if (!passwordEncoder.matches(dto.getPassword(), opt_member.get().getPassword())) {
                check = false;
            }
        }
        if (!check) {
            throw new IllegalArgumentException("email 또는 비밀번호가 일치하지 않습니다");
        }
        return opt_member.get();

    }

//    3. 회원목록조회
    public List<ListDto> findAll(){
        List<Member> list = memberRepository.findAll();
        List<ListDto> listdto = new ArrayList<>();

        for(Member m : list){
            ListDto dto = ListDto.fromEntity(m);
            listdto.add(dto);
        }
        return listdto;

    }



//    4. 내 정보 조회
    public MyDto myinfo(){
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Optional<Member>opt_Member=memberRepository.findByEmail(email);
        Member member = opt_Member.orElseThrow(()->new NoSuchElementException("entity is not found"));
        MyDto dto = MyDto.fromEntity(member);
        return dto;
    }

//    5. 회원 상세내역조회
    public DetailDto findById(Long id){
        Member member= memberRepository.findById(id).orElseThrow(()->new NoSuchElementException("회원이 없다"));
        DetailDto dto = DetailDto.fromEntity(member);
        return dto;
    }


}