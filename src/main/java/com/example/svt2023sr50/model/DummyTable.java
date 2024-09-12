package com.example.svt2023sr50.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dummy_table")
public class DummyTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "groupId")
    private Long groupId;

    @Column(name = "postId")
    private Long postId;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "server_filename")
    private String serverFilename;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;
}
