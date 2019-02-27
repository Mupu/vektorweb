package me.mupu.vektorweb.persistence.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class User extends BaseEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userID")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

}
