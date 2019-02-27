package me.mupu.vektorweb.presentation.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ResetPasswordForm {

    private String resetToken;

    @NotBlank
    @Size(min = 4, max = 32)
    private String password;

    @NotBlank
    @Size(min = 4, max = 32)
    private String repeatPassword;

}
