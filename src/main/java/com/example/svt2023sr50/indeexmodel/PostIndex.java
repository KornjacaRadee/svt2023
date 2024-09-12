package com.example.svt2023sr50.indeexmodel;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@NoArgsConstructor
@Document(indexName = "posts")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class PostIndex {

    @Id
    private Long id;

    @Field(type = FieldType.Text, store = true, name = "postName", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String postName;

    @Field(type = FieldType.Text, store = true, name = "content", analyzer = "serbian_simple", searchAnalyzer = "serbian_simple")
    private String content;
}
