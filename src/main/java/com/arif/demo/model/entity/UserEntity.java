package com.arif.demo.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@ToString
@Table("dm_user")
public class UserEntity extends BaseEntity {
    @Id
    @Column("ID")
    private Long id;

    @Column("USER_KEY")
    private String userKey;

    @Column("USERNAME")
    private String username;

    @Column("PASSWORD_HASH")
    private String passwordHash;

    public static UserEntity of(String username, String passwordHash) {
        var created = LocalDateTime.now();
        return UserEntity.builder()
                .userKey(UUID.randomUUID().toString())
                .username(username)
                .passwordHash(passwordHash)
                .created(created)
                .updated(created)
                .build();
    }
}
