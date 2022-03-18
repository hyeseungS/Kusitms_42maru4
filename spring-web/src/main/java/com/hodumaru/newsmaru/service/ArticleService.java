package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.CategoryEnum;
import com.hodumaru.newsmaru.model.Clip;
import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.ClipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ClipRepository clipRepository;

    public Article addNews(final Article article) {

        return articleRepository.save(article);
    }

    public List<Integer> getGenderData(Long articleId) {
        List<Clip> clipData = clipRepository.findAllByArticleId(articleId);
        List<Integer> genderData = new ArrayList<>(2);
        genderData.add(0);
        genderData.add(0);
        for (Clip clip : clipData) {
            User user = clip.getUser();
            if (user.getGender() != 1) {
                genderData.set(1, genderData.get(1) + 1);
            } else {
                genderData.set(0, genderData.get(0) + 1);
            }
        }
        return genderData;
    }

    public List<Integer> getAgeData(Long articleId) {
        List<Clip> clipData = clipRepository.findAllByArticleId(articleId);
        List<Integer> ageData = new ArrayList<>(6);
        ageData.add(0);
        ageData.add(0);
        ageData.add(0);
        ageData.add(0);
        ageData.add(0);
        ageData.add(0);
        for (Clip clip : clipData) {
            User user = clip.getUser();
            int age = LocalDateTime.now().getYear() - user.getBirthday().getYear() + 1;
            if (age >= 10)
                ageData.set(age / 10 - 1, ageData.get(age / 10 - 1) + 1);
        }
        return ageData;
    }

    // 뉴스 검색
    public List<Article> searchNews(CategoryEnum category, String sortProperty) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortProperty);

        if (category == null)
            return articleRepository.findAll(sort);
        else
            return articleRepository.findByCategory(category, sort);
    }
}
