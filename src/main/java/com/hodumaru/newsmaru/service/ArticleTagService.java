package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.ArticleTag;
import com.hodumaru.newsmaru.model.CategoryEnum;
import com.hodumaru.newsmaru.model.Tag;
import com.hodumaru.newsmaru.repository.ArticleTagRepository;
import com.hodumaru.newsmaru.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleTagService {

    private final ArticleTagRepository articleTagRepository;

    public List<Article> searchNews(Long tagId, CategoryEnum category, String sortProperty) {

        // ArticleTag.Article.sortProperty 로 정렬
        Sort sort = Sort.by(Sort.Direction.DESC, "article." + sortProperty);

        if (category == null)
            return articleTagRepository.findByTagId(tagId, sort);
        else
            return articleTagRepository.findByTagIdAndCategory(tagId, category, sort);
    }

    public void createArticleTags(Article article, List<String> kewords) {
        List<ArticleTag> articleTags = new ArrayList<>();
        for (String keword : kewords) {
            if (articleTagRepository.existsByArticleIdAndTagId(article, keword)) {
                ArticleTag articleTag = ArticleTag.builder().article(article).tag(Tag.builder().name(keword).build()).build();
                articleTags.add(articleTag);
            }
            articleTagRepository.saveAll(articleTags);
        }
    }
}
