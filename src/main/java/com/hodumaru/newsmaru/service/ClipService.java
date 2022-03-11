package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.Clip;
import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.ClipRepository;
import com.hodumaru.newsmaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClipService {

    private final ClipRepository clipRepository;


    public Optional<Clip> findByUserIdAndArticleId(Long userId, Long articleId) {

        return clipRepository.findByUserIdAndArticleId(userId, articleId);
    }

}
