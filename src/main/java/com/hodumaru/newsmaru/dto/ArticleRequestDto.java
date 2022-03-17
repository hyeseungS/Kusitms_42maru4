package com.hodumaru.newsmaru.dto;

import com.hodumaru.newsmaru.model.CategoryEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class ArticleRequestDto {
    @NotEmpty
    private String title;

    @NotEmpty
    @Email
    private String content;

    @NotEmpty
    private String password;

    @NotEmpty
    private CategoryEnum category;
}
