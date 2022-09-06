package com.pentyugov.application.core.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Entity(name = "sec$user")
@Table(name = "SEC_USERS")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "LOGIN")
    @CsvBindByName
    private String login;

    @Column(name = "PASSWORD")
    @CsvBindByName
    private String password;

    @Column(name = "EMAIL")
    @CsvBindByName
    private String email;

}
