package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(final User user) {
        String email = user.getEmail();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByEmail(email); if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다."); }
        // 패스워드 암호화
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }
}
