package me.mupu.vektorweb.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class RegistrationForm {

    @NotBlank
    @Email
    @Size(min=6, max=64)
    private String email;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 4, max = 63)
    private String username;

    @NotBlank
//    @Pattern(regexp = "^[\\x21-\\x7E§´]+$")
    @Size(min = 4, max = 32)
    private String password;

    @NotBlank
    @Size(min = 4, max = 32)
    private String repeatPassword;

}
