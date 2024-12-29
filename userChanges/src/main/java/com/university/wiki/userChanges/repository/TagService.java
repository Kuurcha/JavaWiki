package com.university.wiki.userChanges.repository;

import com.university.wiki.userChanges.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Set<Tag> convertToTags(Set<String> tagNames) {
        return tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName)  // Find the tag by name
                        .orElseGet(() -> {                      // If not found, create a new one
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);  // Save the new tag
                        }))
                .collect(Collectors.toSet());  // Collect into a Set<Tag>
    }
}