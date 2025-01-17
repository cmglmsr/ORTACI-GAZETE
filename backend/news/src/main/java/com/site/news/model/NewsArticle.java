package com.site.news.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.site.news.enums.PoliticalAlignment;
import com.site.news.utils.NewsDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonDeserialize(using = NewsDeserializer.class)
@Table(name = "news_articles")
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String source;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

//    @Column(columnDefinition = "TEXT")
//    private String url;

    @Column(columnDefinition = "TEXT")
    private String img;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime publishDate;

    @Enumerated(EnumType.STRING)
    private PoliticalAlignment alignment;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedNews")
    List<User> likes;

    //@JsonIgnore
    @OneToMany(mappedBy = "newsArticle")
    @JsonBackReference
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "newsArticle")
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();

}
