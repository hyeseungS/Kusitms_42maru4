package com.hodumaru.newsmaru.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsDetailDto {

    private Long id;
    private String title;
    private String content;
}
