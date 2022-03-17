package com.hodumaru.newsmaru.controller;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.security.UserDetailsImpl;
import com.hodumaru.newsmaru.service.ClipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ClipController {

    private final ClipService clipService;
    private final ArticleRepository articleRepository;

    @GetMapping("/clip/{articleId}")
    public String createClip(@PathVariable("articleId") Long articleId,
                             @AuthenticationPrincipal UserDetailsImpl userDetail) {
        Long userId = userDetail.getUser().getId();
        clipService.create(userId, articleId);

        return "redirect:/articles/{articleId}";
    }

    @GetMapping("/unclip/{articleId}")
    public String deleteClip(@PathVariable("articleId") Long articleId,
                             @AuthenticationPrincipal UserDetailsImpl userDetail) {
        Long userId = userDetail.getUser().getId();
        clipService.delete(userId, articleId);

        return "redirect:/articles/{articleId}";
    }
}
