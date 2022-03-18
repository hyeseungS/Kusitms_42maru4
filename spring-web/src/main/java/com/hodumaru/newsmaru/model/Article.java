package com.hodumaru.newsmaru.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

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

    @Lob
    @Column(name = "cloud_image")
    private Blob image;

    public InputStream getImageContent() throws SQLException {
        if (getImage() == null) {
            return null;
        }
        return getImage().getBinaryStream();
    }


}

