package com.hodumaru.newsmaru.controller;

<<<<<<< HEAD
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }


=======
import com.hodumaru.newsmaru.dto.SignupDto;
import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("user") SignupDto signupDto) {
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(@Valid @ModelAttribute("user") SignupDto signupDto, BindingResult bindingResult) {

        if(!signupDto.getPassword().equals(signupDto.getPasswordCheck())) {
            log.info("비밀번호 확인 오류");
            bindingResult.rejectValue("passwordCheck", "", "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult);
            return "/signup";
        }

        User user = User.builder()
                .username(signupDto.getName())
                .email(signupDto.getEmail())
                .password(signupDto.getPassword())
                .gender(signupDto.getGender())
                .birthday(LocalDate.parse(signupDto.getBirthday()))
                .build();
        userService.create(user);
        return "redirect:/";
    }
>>>>>>> main
}
