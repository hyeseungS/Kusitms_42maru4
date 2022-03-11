package com.hodumaru.newsmaru.repository;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}