package com.hodumaru.newsmaru.repository;

import com.hodumaru.newsmaru.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
    boolean existsByName(String name);
}
