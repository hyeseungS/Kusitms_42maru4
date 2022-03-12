package com.hodumaru.newsmaru.controller;

import com.hodumaru.newsmaru.dto.ArticleRequestDto;
import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.CategoryEnum;
import com.hodumaru.newsmaru.security.UserDetailsImpl;
import com.hodumaru.newsmaru.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // 뉴스 보기 페이지
    @GetMapping("/articles")
    public String getNewsList(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        return "newsList";
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
