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
public class ListDto {
    private Long id;
    private String name;
    private String email;

    public static ListDto fromEntity(Member member){
        return ListDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
