package com.hodumaru.newsmaru.repository;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.CategoryEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findById(Long id);

    // 카테고리로 최신순 검색
    List<Article> findByCategory(CategoryEnum category, Sort sort);


}
