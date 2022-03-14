package com.hodumaru.newsmaru.controller;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.security.UserDetailsImpl;
import com.hodumaru.newsmaru.service.ClipService;
import com.hodumaru.newsmaru.service.MyNewsService;
import com.hodumaru.newsmaru.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyNewsController {

    private final MyNewsService mynewsService;
    private final ViewService viewService;

    // MY 뉴스 페이지
    @GetMapping("/mynews")
    public String getMynews(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        List<Article> viewList = mynewsService.getViewArticles(userDetails.getUser());
        model.addAttribute("views", viewList);
        List<Article> clipList = mynewsService.getClipArticles(userDetails.getUser());
        model.addAttribute("clips", clipList);

        return "myNews";
    }

    @GetMapping("/mynews/unview/{articleId}")
    public String deleteView(@PathVariable("articleId") Long articleId,
                             @AuthenticationPrincipal UserDetailsImpl userDetail) {
        Long userId = userDetail.getUser().getId();
        viewService.delete(userId, articleId);

        return "redirect:/mynews";
    }

}
