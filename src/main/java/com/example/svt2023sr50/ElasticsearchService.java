package com.example.svt2023sr50;

import com.example.svt2023sr50.model.Group;
import com.example.svt2023sr50.model.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ElasticsearchService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;




    public void indexPost(Post post) throws IOException {
        IndexRequest indexRequest = new IndexRequest("posts")
                .id(post.getPostId().toString())
                .source(objectMapper.writeValueAsString(post), XContentType.JSON);
        client.index(indexRequest, RequestOptions.DEFAULT);
    }


    public void indexGroup(Group group) throws IOException {
        IndexRequest indexRequest = new IndexRequest("groups")
                .id(group.getId().toString())
                .source(objectMapper.writeValueAsString(group), XContentType.JSON);
        client.index(indexRequest, RequestOptions.DEFAULT);
    }
    public List<Group> searchGroups(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest("groups");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "name", "descripiton")
                .analyzer("custom_analyzer"));

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return StreamSupport
                .stream(searchResponse.getHits().spliterator(), false)
                .map(hit -> {
                    try {
                        return objectMapper.readValue(hit.getSourceAsString(), Group.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }


}
