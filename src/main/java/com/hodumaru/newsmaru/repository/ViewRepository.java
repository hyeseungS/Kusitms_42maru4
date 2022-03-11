package com.hodumaru.newsmaru.repository;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {
    List<View> findAllByUserId(Long userId);
}
