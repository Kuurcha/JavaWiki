package com.university.wiki.userChanges;

import com.university.wiki.userChanges.dto.RecordRequest;
import com.university.wiki.userChanges.entity.Tag;
import com.university.wiki.userChanges.entity.WikiRecord;
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
    private TagService tagService;

    @PostMapping
    public void saveContent(@RequestBody RecordRequest contentRequest) {
        WikiRecord wikiRecord = new WikiRecord();
        wikiRecord.setContent(contentRequest.getContent());
        wikiRecord.setAuthor(contentRequest.getAuthor());
        wikiRecord.setHeader(contentRequest.getAuthor());
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

    @GetMapping
    public Page<WikiRecord> getRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return wikiRecordRepository.findAll(pageable);
    }

    @GetMapping("/search/content")
    public Page<WikiRecord> searchByContent(
            @RequestParam String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return wikiRecordRepository.findByContentContainingIgnoreCase(content, pageable);
    }


    @GetMapping("/search/author")
    public Page<WikiRecord> searchByAuthor(
            @RequestParam String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return wikiRecordRepository.findByAuthorContainingIgnoreCase(author, pageable);
    }

    @GetMapping("/search/tags")
    public Page<WikiRecord> searchByTags(
            @RequestParam Set<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return wikiRecordRepository.findByTagsNameIn(tags, pageable);
    }

}
