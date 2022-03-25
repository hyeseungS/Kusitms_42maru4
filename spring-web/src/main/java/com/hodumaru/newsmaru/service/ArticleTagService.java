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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleTagService {

    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;

    static public class Morpheme {
        final String text;
        final String type;
        Integer count;

        public Morpheme(String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }

    static public class NameEntity implements Comparable<NameEntity> {
        final String text;
        final String type;
        Integer count;

        public NameEntity(String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }

        @Override
        public int compareTo(NameEntity o) {
            return this.count.compareTo(o.count);
        }
    }

    public List<Article> searchNews(Long tagId, CategoryEnum category, String sortProperty) {

        // ArticleTag.Article.sortProperty 로 정렬
        Sort sort = Sort.by(Sort.Direction.DESC, "article." + sortProperty);

        if (category == null)
            return articleTagRepository.findByTagId(tagId, sort);
        else
            return articleTagRepository.findByTagIdAndCategory(tagId, category, sort);
    }


    @Transactional
    public void createArticleTags(Article article, List<String> keywords) {
        List<ArticleTag> articleTags = new ArrayList<>();
        for (String key : keywords) {
            if (!tagRepository.existsByName(key)) {
                Tag tag = Tag.builder().name(key).build();
                tagRepository.save(tag);
            }
            Tag tag = tagRepository.findByName(key).orElse(null);
            if (!articleTagRepository.existsByArticleIdAndTagId(article.getId(), tag.getId())) {
                ArticleTag articleTag = ArticleTag.builder().article(article).tag(tag).build();
                articleTags.add(articleTag);
            }
        }

        articleTagRepository.saveAll(articleTags);
    }
}
