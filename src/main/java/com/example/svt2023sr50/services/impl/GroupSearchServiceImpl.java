package com.example.svt2023sr50.services.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.svt2023sr50.exceptionhandling.exception.MalformedQueryException;
import com.example.svt2023sr50.indeexmodel.DummyIndex;
import com.example.svt2023sr50.indeexmodel.GroupIndex;
import com.example.svt2023sr50.indexrepository.DummyIndexRepository;
import com.example.svt2023sr50.model.Group;
import com.example.svt2023sr50.repository.GroupRepository;
import com.example.svt2023sr50.services.interfaces.SearchGroupService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupSearchServiceImpl implements SearchGroupService {

    private final ElasticsearchOperations elasticsearchTemplate;


    private final GroupRepository groupRepository;
    private final DummyIndexRepository dummyIndexRepository;
    // Search using a list of keywords

    @Override
    public List<GroupIndex> simpleSearch(List<String> keywords, Pageable pageable) {
        List<GroupIndex> combinedResults = new ArrayList<>();

        // Loop through each keyword and perform search
        for (String keyword : keywords) {
            // Step 1: Search groups by name or description
            List<Group> groupSearchResults = groupRepository
                    .findByNameContainingOrDescripitonContaining(keyword,keyword);
            for (Group group : groupSearchResults) {
                GroupIndex groupIndex = new GroupIndex();
                groupIndex.setId(group.getId());
                groupIndex.setName(group.getName());
                groupIndex.setDescription(group.getDescripiton());
                combinedResults.add(groupIndex);
            }

            // Step 2: Search PDF content (DummyIndex) and return groups associated with the PDFs
            List<DummyIndex> dummyIndexes = dummyIndexRepository.findByContentSrContainingOrTitleContaining(keyword,keyword);
            for (DummyIndex dummyIndex : dummyIndexes) {
                groupRepository.findById(dummyIndex.getGroupId()).ifPresent(group -> {
                    GroupIndex groupIndex = new GroupIndex();
                    groupIndex.setId(group.getId());
                    groupIndex.setName(group.getName());
                    groupIndex.setDescription(group.getDescripiton());
                    combinedResults.add(groupIndex);
                });
            }
        }

        return combinedResults;
    }
    @Override
    public Page<GroupIndex> advancedSearch(List<String> expression, Pageable pageable) {
        if (expression.size() != 3) {
            throw new MalformedQueryException("Search query malformed.");
        }

        String operation = expression.get(1);
        expression.remove(1);
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildAdvancedSearchQuery(expression, operation))
                        .withPageable(pageable);

        return runQuery(searchQueryBuilder.build());
    }

    private Query buildSimpleSearchQuery(List<String> tokens) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            tokens.forEach(token -> {
                b.should(sb -> sb.match(
                        m -> m.field("name").fuzziness(Fuzziness.ONE.asString()).query(token)));
                b.should(sb -> sb.match(m -> m.field("description").query(token)));
            });
            return b;
        })))._toQuery();
    }

    private Query buildAdvancedSearchQuery(List<String> operands, String operation) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            var field1 = operands.get(0).split(":")[0];
            var value1 = operands.get(0).split(":")[1];
            var field2 = operands.get(1).split(":")[0];
            var value2 = operands.get(1).split(":")[1];

            switch (operation) {
                case "AND":
                    b.must(sb -> sb.match(
                            m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.must(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "OR":
                    b.should(sb -> sb.match(
                            m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.should(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "NOT":
                    b.must(sb -> sb.match(
                            m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.mustNot(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
            }

            return b;
        })))._toQuery();
    }

    private Page<GroupIndex> runQuery(NativeQuery searchQuery) {

        var searchHits = elasticsearchTemplate.search(searchQuery, GroupIndex.class,
                IndexCoordinates.of("groups"));

        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());

        return (Page<GroupIndex>) SearchHitSupport.unwrapSearchHits(searchHitsPaged);
    }
}
