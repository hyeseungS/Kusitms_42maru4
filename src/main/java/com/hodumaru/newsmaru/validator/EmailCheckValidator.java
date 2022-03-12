package com.hodumaru.newsmaru.validator;

import com.hodumaru.newsmaru.dto.SignupDto;
import com.hodumaru.newsmaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class EmailCheckValidator extends AbstractValidator<SignupDto> {
    private final UserRepository userRepository;

    @Override
    protected void doValidate(SignupDto dto, Errors errors) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일 입니다.");
        }
    }
}

