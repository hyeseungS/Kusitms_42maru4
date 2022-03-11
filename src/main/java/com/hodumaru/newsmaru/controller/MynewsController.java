package com.hodumaru.newsmaru.controller;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.security.UserDetailsImpl;
import com.hodumaru.newsmaru.service.MynewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MynewsController {

    private final MynewsService mynewsService;

    // MY 뉴스 페이지
    @GetMapping("/mynews")
    public String getMynews(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());

        List<Article> viewList = mynewsService.getViewArticles(userDetails.getUser());
        model.addAttribute("view", viewList);
        List<Article> clipList = mynewsService.getClipArticles(userDetails.getUser());
        model.addAttribute("clip", clipList);

        return "myNews";
    }
}
