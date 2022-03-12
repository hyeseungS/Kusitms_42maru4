package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Clip;
import com.hodumaru.newsmaru.repository.ClipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClipService {

    private final ClipRepository clipRepository;


    public Optional<Clip> findByUserIdAndArticleId(Long userId, Long articleId) {

        return clipRepository.findByUserIdAndArticleId(userId, articleId);
    }

}
