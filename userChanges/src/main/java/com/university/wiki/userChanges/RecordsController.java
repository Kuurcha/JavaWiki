package com.university.wiki.userChanges;

import com.university.wiki.userChanges.dto.RecordRequest;
import com.university.wiki.userChanges.entity.Tag;
import com.university.wiki.userChanges.entity.WikiRecord;
import com.university.wiki.userChanges.service.RecordRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.university.wiki.userChanges.repository.TagService;
import com.university.wiki.userChanges.repository.WikiRecordRepository;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class RecordsController {
    @Autowired
    private WikiRecordRepository
            wikiRecordRepository;

    @Autowired
    private RecordRepositoryService wikiRecordService;

    @Autowired
    private TagService tagService;

    @PostMapping
    public void saveContent(@RequestBody RecordRequest contentRequest) {
        WikiRecord wikiRecord = new WikiRecord();
        wikiRecord.setContent(contentRequest.getContent());
        wikiRecord.setAuthor(contentRequest.getAuthor());
        wikiRecord.setHeader(contentRequest.getHeader());
        wikiRecord.setDate(LocalDateTime.now());

        Set<Tag> tags = tagService.convertToTags(contentRequest.getTags());

        wikiRecord.setTags(tags);
        wikiRecordRepository.save(wikiRecord);
    }

    @GetMapping("/{id}")
    public WikiRecord getRecordById(@PathVariable Long id) {
        return wikiRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with ID: " + id));
    }

    /**
     * Get filtered WikiRecords with pagination.
     *
     * @param content - The content to filter by (optional).
     * @param author - The author to filter by (optional).
     * @param tags - The set of tags to filter by (optional).
     * @param page - The page number (default is 0).
     * @param size - The number of records per page (default is 10).
     * @return A page of filtered WikiRecords.
     */
    @GetMapping
    public Page<WikiRecord> getRecords(
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Set<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return wikiRecordService.getRecords(content, author, tags, page, size);
    }

    /**
     * Get the count of WikiRecords based on filters.
     *
     * @param content - The content to filter by (optional).
     * @param author - The author to filter by (optional).
     * @param tags - The set of tags to filter by (optional).
     * @return The total count of filtered WikiRecords.
     */
    @GetMapping("/count")
    public long countRecords(
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Set<String> tags
    ) {
        return wikiRecordService.countRecords(content, author, tags);
    }



}
