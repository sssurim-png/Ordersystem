package com.example.post.order.domain;

import com.example.post.common.DateTime;
import com.example.post.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class Orders extends DateTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id",foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),nullable = false)
    private Member member;
    @Builder.Default
    private Status status = Status.ORDERED;

}
