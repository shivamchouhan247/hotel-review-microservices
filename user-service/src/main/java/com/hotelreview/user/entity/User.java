package com.hotelreview.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "ABOUT", columnDefinition = "TEXT", nullable = false)
    private String about;
    @Column(updatable = false)
    private Date creationDate;

    @Transient
    @Builder.Default
    private List<Rating> ratings = new ArrayList<>();

}
