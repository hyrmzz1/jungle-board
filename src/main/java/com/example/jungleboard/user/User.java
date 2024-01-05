package com.example.jungleboard.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Table(name = "user_table")
@Getter
@Entity
public class User{
    @Id
    @Column(name = "member_id")
    private Integer MemberId;

    private String UserId;

    private String UserName;

    private String UserInfo;

    @Column
    @Email
    private String UserEmail;
}
