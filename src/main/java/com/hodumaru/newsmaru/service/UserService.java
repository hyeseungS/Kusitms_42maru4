package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(final User user) {
        return userRepository.save(user);
    }
}
