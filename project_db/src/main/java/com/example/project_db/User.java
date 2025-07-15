package com.example.project_db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id // 이 필드가 PK임을 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB의 Auto Increment 기능 사용
    @Column(name = "userIndex")
    private Long userIndex;

    @Column(name = "userid")
    private String userid;

    @Column(name = "userPassword")
    private String userPassword;

    @Column(name = "userAuthority")
    private int userAuthority;

    @Column(name = "userAddress")
    private String userAddress;

    @Column(name = "cert")
    private String cert;
}
