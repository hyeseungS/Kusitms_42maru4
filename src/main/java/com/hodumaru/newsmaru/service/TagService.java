package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.Tag;
import com.hodumaru.newsmaru.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public void createTags(List<Tag> tags) {
        tagRepository.saveAll(tags);
    }
}
