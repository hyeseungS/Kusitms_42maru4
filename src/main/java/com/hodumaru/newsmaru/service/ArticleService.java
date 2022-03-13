package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.CategoryEnum;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article addNews(final Article article) {
        return articleRepository.save(article);
    }


    public List<Article> searchNews(CategoryEnum category, String sortProperty) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortProperty);

        if(category == null)
            return articleRepository.findAll(sort);
        else
            return articleRepository.findByCategory(category, sort);
    }
}
