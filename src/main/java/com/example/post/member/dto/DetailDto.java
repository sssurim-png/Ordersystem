package com.example.post.member.dto;

import com.example.post.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DetailDto {
    private Long id;
    private String name;
    private String email;

    public static DetailDto fromEntity(Member member){
        return DetailDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
