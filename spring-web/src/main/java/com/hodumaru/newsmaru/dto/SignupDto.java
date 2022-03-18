package com.hodumaru.newsmaru.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignupDto {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자여야 합니다.")
    private String password;

    @NotEmpty
    private String passwordCheck;

    @NotNull
    private int gender;

    @NotEmpty
    private String birthday;
}
