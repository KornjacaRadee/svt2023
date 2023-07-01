package com.example.svt2023sr50.model;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column
    private String postName;

    @Column
    private String content;

    @Column
    private String url;

    @Column
    private LocalDateTime creationDate;

    @ManyToOne
    private User user;

    public Post() {
        this.creationDate = LocalDateTime.now();
    }

}
