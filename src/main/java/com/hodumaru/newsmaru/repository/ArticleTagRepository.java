package com.hodumaru.newsmaru.repository;

import com.hodumaru.newsmaru.model.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    List<ArticleTag> findByTagId(Long id);
    List<ArticleTag> findByArticleId(Long id);
}
