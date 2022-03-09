package com.hodumaru.newsmaru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NewsmaruApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsmaruApplication.class, args);
    }

}
