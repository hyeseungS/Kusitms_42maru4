package com.hodumaru.newsmaru.testdata;

import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;


@Component
public class TestDataRunner implements ApplicationRunner {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        User testUser1 = new User("호두마루", "hodu@maru.com", passwordEncoder.encode("123"), 1, LocalDate.now());

        testUser1 = userRepository.save(testUser1);
    }
}
