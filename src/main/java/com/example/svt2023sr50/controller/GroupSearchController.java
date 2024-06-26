package com.example.svt2023sr50.controller;

import com.example.svt2023sr50.dto.SearchQueryDTO;
import com.example.svt2023sr50.indeexmodel.GroupIndex;
import com.example.svt2023sr50.services.impl.GroupSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/groups")
@RequiredArgsConstructor
public class GroupSearchController {

    private final GroupSearchServiceImpl searchService;

    @PostMapping("/simple")
    public Page<GroupIndex> simpleSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                         Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public Page<GroupIndex> advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                           Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.keywords(), pageable);
    }
}
