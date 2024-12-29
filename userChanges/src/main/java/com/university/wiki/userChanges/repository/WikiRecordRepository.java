package com.university.wiki.userChanges.repository;

import com.university.wiki.userChanges.entity.WikiRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WikiRecordRepository extends JpaRepository<WikiRecord, Long> {

    Page<WikiRecord> findByContentContainingIgnoreCase(String content, Pageable pageable);


    Page<WikiRecord> findByAuthorContainingIgnoreCase(String author, Pageable pageable);


    Page<WikiRecord> findByTagsNameIn(Set<String> tagNames, Pageable pageable);


}