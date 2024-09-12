package com.example.svt2023sr50.controller;

import com.example.svt2023sr50.dto.SearchQueryDTO;
import com.example.svt2023sr50.indeexmodel.DummyIndex;
import com.example.svt2023sr50.indeexmodel.GroupIndex;
import com.example.svt2023sr50.indeexmodel.PostIndex;
import com.example.svt2023sr50.services.impl.GroupSearchServiceImpl;
import com.example.svt2023sr50.services.impl.SearchServiceImpl;
import com.example.svt2023sr50.services.interfaces.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/search/groups")
@RequiredArgsConstructor
public class GroupSearchController {

    private final GroupSearchServiceImpl searchService;
    private final SearchServiceImpl srchService;
    private final IndexingService indexingService;

    @PostMapping("/simple")
    public List<GroupIndex> simpleSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                         Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public Page<GroupIndex> advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                           Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.keywords(), pageable);
    }

    @PostMapping("/{groupId}/upload-pdf")
    public ResponseEntity<String> uploadGroupPdf(@PathVariable Long groupId,
                                                 @RequestParam("documentFile") MultipartFile documentFile) {
        try {
            // Index the PDF file and associate it with the group
            String fileName = indexingService.indexDocumentForGroup(groupId, documentFile);
            return ResponseEntity.ok("PDF file " + fileName + " uploaded and indexed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload PDF: " + e.getMessage());
        }
    }

    @PostMapping("/{groupId}/upload-pdf-post")
    public ResponseEntity<String> uploadPostPdf(@PathVariable Long groupId,
                                                 @RequestParam("documentFile") MultipartFile documentFile) {
        try {
            // Index the PDF file and associate it with the group
            String fileName = indexingService.indexDocumentForPosts(groupId, documentFile);
            return ResponseEntity.ok("PDF file " + fileName + " uploaded and indexed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload PDF: " + e.getMessage());
        }
    }

    @GetMapping("/groups-pdf")
    public List<GroupIndex> searchGroupsAndPdfContent(@RequestBody SearchQueryDTO advancedSearchQuery, Pageable pageable) {
        return srchService.searchGroupsAndPdfContent(advancedSearchQuery.keywords(), pageable);
    }

    @GetMapping("/posts-pdf-search")
    public List<PostIndex> searchGroupsAndPdfPostsContent(@RequestBody SearchQueryDTO advancedSearchQuery, Pageable pageable) {
        return srchService.searchGroupsAndPdfPostsContent(advancedSearchQuery.keywords(), pageable);
    }


}
