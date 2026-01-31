package com.example.post.member.controller;

import com.example.post.common.auth.JwtTokenProvider;
import com.example.post.member.domain.Member;
import com.example.post.member.dto.*;
import com.example.post.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;
    private JwtTokenProvider jwtTokenProvider;

    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //    1. 회원가입
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateDto dto){
        Long id =memberService.save(dto); //이메일 찾아서 있으면 에러, 없으면 저장
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

//    2. user로그인
    @PostMapping("/doLogin")
    public String login(@RequestBody MemberLoginReqDto dto){
        Member member = memberService.login(dto);

        String accessToken =jwtTokenProvider.createToken(member);
//        refresh생성
        MemberLoginResDto memberLoginResDto=MemberLoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(null)
                .build();
        return accessToken;
    }


//    3. 회원목록조회
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')") //관리자만 조회할 수 있도록
    public List<MemberListDto> list(){
        List<MemberListDto> dto = memberService.findAll();
        return dto;
    }


//    4. 내 정보 조회
    @GetMapping("/myinfo")
    public MyDto myinfo(){
        MyDto dto =memberService.myinfo();
        return dto;
    }


//    5. 회원 상세내역조회
    @GetMapping("/detail/{id}")
    public MemberDtailDto detail(@PathVariable Long id){
        return memberService.findById(id);

    }



}
