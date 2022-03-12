package com.hodumaru.newsmaru.repository;

import com.hodumaru.newsmaru.model.Clip;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClipRepository extends JpaRepository<Clip, Long> {
    List<Clip> findAllByUserId(Long userId, Sort sort);

    Optional<Clip> findByUserIdAndArticleId(Long userId, Long ArticleId);

    void deleteByUserIdAndArticleId(Long userId, Long ArticleId);
}
