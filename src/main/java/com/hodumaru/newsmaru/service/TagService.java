package com.hodumaru.newsmaru.service;

import com.hodumaru.newsmaru.model.ArticleTag;
import com.hodumaru.newsmaru.model.Tag;
import com.hodumaru.newsmaru.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public void createTags(List<String> kewords) {
        List<Tag> tags = new ArrayList<>();
        for(String keword : kewords) {
            if(!tagRepository.existsByName(keword)) {
                Tag tag = Tag.builder().name(keword).build();
                tags.add(tag);
            }
        }
        tagRepository.saveAll(tags);
    }
}
