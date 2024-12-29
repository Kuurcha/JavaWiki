package com.university.wiki.userChanges.repository;

import com.university.wiki.userChanges.entity.WikiRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface WikiRecordRepository extends JpaRepository<WikiRecord, Long>, JpaSpecificationExecutor<WikiRecord> {


    long count();

}