package com.university.wiki.userChanges.repository;

import com.university.wiki.userChanges.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository  extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
