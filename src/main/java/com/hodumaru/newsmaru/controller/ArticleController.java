package com.hodumaru.newsmaru.controller;

import com.hodumaru.newsmaru.dto.ArticleRequestDto;
import com.hodumaru.newsmaru.dto.NewsDetailDto;
import com.hodumaru.newsmaru.model.*;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.security.UserDetailsImpl;
import com.hodumaru.newsmaru.service.ArticleService;
import com.hodumaru.newsmaru.service.ClipService;
import com.hodumaru.newsmaru.service.ViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final ClipService clipService;
    private final ViewService viewService;

    // 뉴스 보기 페이지
    @GetMapping("/articles")
    public String getNewsList(Model model) {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        if(articles != null)
            model.addAttribute("articles", articles);
        return "newsList";
    }

    @Transactional
    @GetMapping("/articles/{articleId}")
    public String getNewsDetail(@PathVariable("articleId") Long articleId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getUser().getId();

        // 스크랩 여부
        Clip clip = clipService.findByUserIdAndArticleId(userId, articleId).orElse(null);
        boolean isClipped = (clip != null);
        model.addAttribute("isClipped", isClipped);

        // 뉴스 정보
        Article article = articleRepository.findById(articleId).get();
        NewsDetailDto newsDetailDto = NewsDetailDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .build();

        model.addAttribute("NewsDetailDto", newsDetailDto);

        // 조회 여부 확인 및 생성
        View view = viewService.findByUserIdAndArticleId(userId, articleId).orElse(null);
        if(view == null) {
            viewService.create(userId, articleId);
        }

        // 태그 정보



        return "newsDetail";
    }

    // 뉴스 등록 페이지
    @GetMapping("/articles/new")
    public String getAddNews(Model model) {

        model.addAttribute("articles", CategoryEnum.values());
        return "addNews";
    }

    // 뉴스 등록하기
    @PostMapping("/new")
    public String addArticle(ArticleRequestDto articleRequestDto) {

        Article article = Article.builder()
                .title(articleRequestDto.getTitle())
                .category(articleRequestDto.getCategory())
                .content(articleRequestDto.getContent())
                .build();
        articleService.addNews(article);

        return "redirect:/articles/new";
    }
}
