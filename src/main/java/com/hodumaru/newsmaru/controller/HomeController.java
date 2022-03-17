package com.hodumaru.newsmaru.controller;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleRepository articleRepository;

    // 랜딩 페이지
    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        if(articles != null)
            model.addAttribute("articles", articles);
        return "redirect:/articles";
    }
}
