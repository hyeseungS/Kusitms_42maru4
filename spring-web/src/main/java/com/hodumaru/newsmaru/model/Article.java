package com.hodumaru.newsmaru.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class Article extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "article_id")
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Size(max = 5000)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CategoryEnum category;

    @Column(name = "clip_count")
    private int clipCount = 0;

    @Column(name = "view_count")
    private int viewCount = 0;

    public Article(String title, String content, CategoryEnum category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }
}
