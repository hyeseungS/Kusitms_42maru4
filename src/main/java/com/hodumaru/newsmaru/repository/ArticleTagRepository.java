package com.hodumaru.newsmaru.repository;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.ArticleTag;
import com.hodumaru.newsmaru.model.CategoryEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    List<ArticleTag> findByTagId(Long id);

    List<ArticleTag> findByArticleId(Long id);

    // 태그, 정렬 검색
    @Query("select at.article from ArticleTag at " +
            "where at.tag.id = :tagId")
    List<Article> findByTagId(@Param("tagId") Long tagId, Sort sort);


    // 태그, 카테고리, 정렬 검색
    @Query("select at.article from ArticleTag at " +
            "where at.tag.id = :tagId and at.article.category = :category")
    List<Article> findByTagIdAndCategory(@Param("tagId") Long tagId, @Param("category") CategoryEnum category, Sort sort);

    boolean existsByArticleIdAndTagId(Long articleId, Long tagId);
}
