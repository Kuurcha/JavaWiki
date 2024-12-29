package com.university.wiki.userChanges.service;

import com.university.wiki.userChanges.entity.WikiRecord;
import com.university.wiki.userChanges.repository.WikiRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RecordRepositoryService    {
    @Autowired
    private WikiRecordRepository wikiRecordRepository;


    /**
     * Get filtered WikiRecords by content, author, and tags with pagination.
     * @param content - The content to search for (optional).
     * @param author - The author to search for (optional).
     * @param tags - The set of tags to search for (optional).
     * @param page - The page number (default is 0).
     * @param size - The number of records per page (default is 10).
     * @return A page of filtered WikiRecords.
     */
    public Page<WikiRecord> getRecords(String content, String author, Set<String> tags, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<WikiRecord> spec = Specification.where(null);

        // Add content filter
        if (content != null && !content.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + content.toLowerCase() + "%"));
        }

        // Add author filter
        if (author != null && !author.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
        }

        // Add tags filter (assuming you have a many-to-many relationship with tags)
        if (tags != null && !tags.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.join("tags").get("name").in(tags));
        }
        Page<WikiRecord> result = wikiRecordRepository.findAll(spec, pageable);
        return result;
    }

    /**
     * Get the count of filtered WikiRecords by content, author, and tags.
     * @param content - The content to search for (optional).
     * @param author - The author to search for (optional).
     * @param tags - The set of tags to search for (optional).
     * @return The total count of filtered WikiRecords.
     */
    public long countRecords(String content, String author, Set<String> tags) {
        Specification<WikiRecord> spec = Specification.where(null);

        // Add content filter
        if (content != null && !content.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + content.toLowerCase() + "%"));
        }

        // Add author filter
        if (author != null && !author.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
        }

        // Add tags filter
        if (tags != null && !tags.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.join("tags").get("name").in(tags));
        }

        return wikiRecordRepository.count(spec);
    }
}
