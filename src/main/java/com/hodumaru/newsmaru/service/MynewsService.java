package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.Clip;
import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.model.View;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.ClipRepository;
import com.hodumaru.newsmaru.repository.UserRepository;
import com.hodumaru.newsmaru.repository.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MynewsService {

    private final ArticleRepository articleRepository;
    private final ViewRepository viewRepository;
    private final ClipRepository clipRepository;

    // 회원 ID 가 조회한 기사 조회
    public List<Article> getViewArticles(
            User user
    ) {
        Long userId = user.getId();
        List<View> viewList = viewRepository.findAllByUserId(userId);
        List<Article> articleList = new ArrayList<>();
        for(View view : viewList) {
            Article article = articleRepository.findById(view.getArticle().getId())
                    .orElseThrow(() -> new NullPointerException("해당 기사 아이디가 존재하지 않습니다."));
            articleList.add(article);
        }
        return articleList;
    }

    // 회원 ID 가 스크랩한 기사 조회
    public List<Article> getClipArticles(
            User user
    ) {
        Long userId = user.getId();
        List<Clip> clipList = clipRepository.findAllByUserId(userId);
        List<Article> articleList = new ArrayList<>();
        for(Clip clip : clipList) {
            Article article = articleRepository.findById(clip.getArticle().getId())
                    .orElseThrow(() -> new NullPointerException("해당 기사 아이디가 존재하지 않습니다."));
            articleList.add(article);
        }
        return articleList;
    }
}
