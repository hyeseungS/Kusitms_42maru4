package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.Clip;
import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.ClipRepository;
import com.hodumaru.newsmaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClipService {

    private final ClipRepository clipRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public Optional<Clip> findByUserIdAndArticleId(Long userId, Long articleId) {
        return clipRepository.findByUserIdAndArticleId(userId, articleId);
    }

    public Clip create(Long userId, Long articleId) {
        User user = userRepository.findById(userId).get();
        Article article = articleRepository.findById(articleId).get();
        Clip clip = Clip.builder().user(user).article(article).build();

        return clipRepository.save(clip);
    }

    @Transactional
    public void delete(Long userId, Long articleId) {
        clipRepository.deleteByUserIdAndArticleId(userId, articleId);
    }
}
