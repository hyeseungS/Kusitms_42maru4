package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.model.View;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.UserRepository;
import com.hodumaru.newsmaru.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ViewService {

    private final ViewRepository viewRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public Optional<View> findByUserIdAndArticleId(Long userId, Long articleId) {

        return viewRepository.findByUserIdAndArticleId(userId, articleId);
    }

    @Transactional
    public View create(Long userId, Long articleId) {
        User user = userRepository.findById(userId).get();
        Article article = articleRepository.findById(articleId).get();
        View view = View.builder().user(user).article(article).build();

        article.setViewCount(article.getViewCount() + 1); // 조회 수 증가


        return viewRepository.save(view);
    }

    @Transactional
    public void delete(Long userId, Long articleId) {
        viewRepository.deleteByUserIdAndArticleId(userId, articleId);
        Article article = articleRepository.findById(articleId).get();
        article.setViewCount(article.getViewCount() - 1); // 조회 수 감소

    }
}
