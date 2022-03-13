package com.hodumaru.newsmaru.controller;

import com.hodumaru.newsmaru.dto.ArticleRequestDto;
import com.hodumaru.newsmaru.dto.NewsDetailDto;
import com.hodumaru.newsmaru.model.*;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.TagRepository;
import com.hodumaru.newsmaru.security.UserDetailsImpl;
import com.hodumaru.newsmaru.service.ArticleService;
import com.hodumaru.newsmaru.service.ArticleTagService;
import com.hodumaru.newsmaru.service.ClipService;
import com.hodumaru.newsmaru.service.ViewService;
import com.hodumaru.newsmaru.summary.SummaryRequest;
import com.hodumaru.newsmaru.summary.SummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final ArticleTagService articleTagService;
    private final TagRepository tagRepository;
    private final ClipService clipService;
    private final ViewService viewService;

    // 뉴스 보기 페이지
    @GetMapping("/articles")
    public String getNewsList(Model model) {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        if(articles != null)
            model.addAttribute("articles", articles);
        model.addAttribute("checkedCategory", null);
        model.addAttribute("sort", "createdAt");
        model.addAttribute("categories", CategoryEnum.values());
        return "newsList";
    }


    // 뉴스 검색
    @GetMapping("/articles/search")
    public String searchNews(Model model, @RequestParam(name = "tag") String tagName,
                             @RequestParam("category") String categoryEnum, @RequestParam String sort) {

        CategoryEnum category = null;
        if(!categoryEnum.equals(""))
            category = CategoryEnum.valueOf(categoryEnum);

        model.addAttribute("checkedCategory", category);
        model.addAttribute("sort", sort);
        model.addAttribute("categories", CategoryEnum.values());

        // 태그 검색인 경우 -> ArticleTag 를 통해 조회
        if (!tagName.equals("")) {
            List<Article> articles = new ArrayList<>();
            Tag tag = tagRepository.findByName(tagName).orElse(null);
            if (tag != null)
                articles = articleTagService.searchNews(tag.getId(), category, sort);
            model.addAttribute("articles", articles);
        }
        // 태그 검색이 아닌 경우 -> Article 을 통해 조회
        else {
            List<Article> articles = articleService.searchNews(category, sort);
            model.addAttribute("articles", articles);
        }

        return "newsList";
    }


    // 뉴스 상세 페이지
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

        // 조회 여부 확인
        View view = viewService.findByUserIdAndArticleId(userId, articleId).orElse(null);
        if(view == null) {
            viewService.create(userId, articleId); // 조회 생성
            article.setViewCount(article.getViewCount() + 1); //조회수 증가
        }

        // 태그 정보



        return "newsDetail";
    }

    // 생성 요약 API 연동
    @PostMapping("/articles/{articleId}/summarize")
    @ResponseBody
    public SummaryResponse getSummary(@PathVariable("articleId") Long articleId,
                                      @RequestBody SummaryRequest request) {
        String url = "http://kr.textsum.42maru.com/predict";
        String username = "kusitms";
        String password = "zest@42maru";

        // Basic Auth 헤더 설정
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authHeader);

        // Post 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(request, httpHeaders), String.class);
        String body = response.getBody().replace("\"summaries\":[\"", "").replace("\"]", "");

        SummaryResponse summaryResponse = new SummaryResponse(body, articleId);
        return summaryResponse;

    }

    // 뉴스 등록 페이지
    @GetMapping("/articles/new")
    public String getAddNews(Model model) {

        model.addAttribute("articles", CategoryEnum.values());
        return "addNews";
    }

    // 뉴스 등록하기
    @PostMapping("articles/new")
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
