package com.hodumaru.newsmaru.controller;

import com.hodumaru.newsmaru.dto.ArticleRequestDto;
import com.hodumaru.newsmaru.dto.NewsDetailDto;
import com.hodumaru.newsmaru.model.*;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.ArticleTagRepository;
import com.hodumaru.newsmaru.repository.TagRepository;
import com.hodumaru.newsmaru.security.UserDetailsImpl;
import com.hodumaru.newsmaru.service.*;
import com.hodumaru.newsmaru.summary.SummaryRequest;
import com.hodumaru.newsmaru.summary.SummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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

import java.io.*;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final ArticleTagService articleTagService;
    private final ArticleTagRepository articleTagRepository;
    private final TagRepository tagRepository;
    private final ClipService clipService;
    private final ViewService viewService;
    private final KeywordService keywordService;
    private final ResourceLoader resourceLoader;

    @Value("${summary-api-username}")
    private String username;

    @Value("${summary-api-password}")
    private String password;

    // ?????? ?????? ?????????
    @GetMapping("/articles")
    public String getNewsList(Model model) {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        if (articles != null)
            model.addAttribute("articles", articles);
        model.addAttribute("checkedCategory", null);
        model.addAttribute("sort", "createdAt");
        model.addAttribute("categories", CategoryEnum.values());
        return "newsList";

    }


    // ?????? ??????
    @GetMapping("/articles/search")
    public String searchNews(Model model, @RequestParam(name = "tag") String tagName,
                             @RequestParam("category") String categoryEnum, @RequestParam String sort) {

        CategoryEnum category = null;
        if (!categoryEnum.equals(""))
            category = CategoryEnum.valueOf(categoryEnum);

        model.addAttribute("checkedCategory", category);
        model.addAttribute("sort", sort);
        model.addAttribute("categories", CategoryEnum.values());
        model.addAttribute("tag", tagName);


        // ?????? ????????? ?????? -> ArticleTag ??? ?????? ??????
        if (!tagName.equals("")) {
            List<Article> articles = new ArrayList<>();
            Tag tag = tagRepository.findByName(tagName).orElse(null);
            if (tag != null) {
                articles = articleTagService.searchNews(tag.getId(), category, sort);
            }
            model.addAttribute("articles", articles);
        }
        // ?????? ????????? ?????? ?????? -> Article ??? ?????? ??????
        else {
            List<Article> articles = articleService.searchNews(category, sort);
            model.addAttribute("articles", articles);
        }

        return "newsList";
    }


    // ?????? ?????? ?????????
    @GetMapping("/articles/{articleId}")
    public String getNewsDetail(@PathVariable("articleId") Long articleId, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();

        // ?????? ?????? model ????????? ??????
        getNewsDetailData(articleId, model, userId);

        // ????????? ?????????
        List<Integer> genderData = articleService.getGenderData(articleId);
        model.addAttribute("GenderDatas", genderData);
        List<Integer> ageData = articleService.getAgeData(articleId);
        model.addAttribute("AgeDatas", ageData);
        return "newsDetail";
    }

    private void getNewsDetailData(Long articleId, Model model, Long userId) {
        // ????????? ??????
        Clip clip = clipService.findByUserIdAndArticleId(userId, articleId).orElse(null);
        boolean isClipped = (clip != null);
        model.addAttribute("isClipped", isClipped);

        // ?????? ??????
        Article article = articleRepository.findById(articleId).get();
        NewsDetailDto newsDetailDto = NewsDetailDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .build();

        model.addAttribute("NewsDetailDto", newsDetailDto);

        // ?????? ????????????
        try {
            InputStream binaryStream = article.getImage().getBinaryStream();
            byte[] image = binaryStream.readAllBytes();
            String imageStr = Base64.encodeBase64String(image);

            model.addAttribute("wordcloud", imageStr);

        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }


        // ?????? ?????? ??????
        View view = viewService.findByUserIdAndArticleId(userId, articleId).orElse(null);
        if (view == null) {
            viewService.create(userId, articleId);
        }

        // ?????? ??????
        List<Tag> tags = articleTagRepository.findByArticleId(articleId).stream()
                .map(articleTag -> articleTag.getTag())
                .collect(Collectors.toList());
        model.addAttribute("tags", tags);

    }

    // ?????? ?????? API ??????
    @PostMapping("/articles/{articleId}/summarize")
    @ResponseBody
    public SummaryResponse getSummary(@PathVariable("articleId") Long articleId,
                                      @RequestBody SummaryRequest request) {
        String url = "http://kr.textsum.42maru.com/predict";

        // Basic Auth ?????? ??????
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authHeader);

        // Post ??????
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(request, httpHeaders), String.class);
        String body = response.getBody().replace("\"summaries\":[\"", "").replace("\"]", "");

        SummaryResponse summaryResponse = new SummaryResponse(body, articleId);
        return summaryResponse;

    }

    // ?????? ?????? ?????????
    @GetMapping("/articles/new")
    public String getAddNews(Model model) {

        model.addAttribute("articles", CategoryEnum.values());
        return "addNews";
    }

    // ?????? ????????????
    @PostMapping("/articles/new")
    public String addArticle(ArticleRequestDto articleRequestDto) throws SQLException, IOException {

        Article article = Article.builder()
                .title(articleRequestDto.getTitle())
                .category(articleRequestDto.getCategory())
                .content(articleRequestDto.getContent())
                .build();
        articleService.addNews(article);

        try {
//             ?????????????????? ?????? (?????????)
            String cloudUrl = "http://ec2-3-35-8-193.ap-northeast-2.compute.amazonaws.com:4000/wordcloud/" + article.getId();
            RestTemplate restTemplate1 = new RestTemplate();
            ResponseEntity<String> cloudResponse = restTemplate1.getForEntity(cloudUrl, String.class);

            // ???????????? ???????????? ?????? (?????????)
            String tagUrl = "http://ec2-3-35-8-193.ap-northeast-2.compute.amazonaws.com:4000/extract/"+article.getId();
            RestTemplate restTemplate2 = new RestTemplate();
            ResponseEntity<String> tagResponse = restTemplate2.getForEntity(tagUrl, String.class);

            // ???????????? ???????????? ?????? (??????)
            String newsContent = article.getContent();
            List<String> keywords = keywordService.searchTags(newsContent);
            articleTagService.createArticleTags(article, keywords);

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return "redirect:/articles";

    }

}