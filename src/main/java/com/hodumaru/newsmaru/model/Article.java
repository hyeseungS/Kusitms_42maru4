package com.hodumaru.newsmaru.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class Article {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate postDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

}
