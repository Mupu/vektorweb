package me.mupu.vektorweb.dao;

import lombok.Getter;

@Getter
public class User {

    private String username;

    private String email;

    private String confirmationToken;

    private String resetPasswordToken;
}
