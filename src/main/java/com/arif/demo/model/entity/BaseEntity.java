package com.arif.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @CreatedDate
    @Column("CREATED")
    private LocalDateTime created;

    @Column("UPDATED")
    @LastModifiedDate
    private LocalDateTime updated;
}
