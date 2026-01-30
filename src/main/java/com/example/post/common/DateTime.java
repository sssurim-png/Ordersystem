package com.example.post.common;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public class DateTime {
    @UpdateTimestamp
    private LocalDateTime updatedTime;
}
