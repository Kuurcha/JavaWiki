package com.university.wiki.userChanges.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RecordRequest {
    private String content;
    private String header;
    private String author;
    private Set<String> tags;
}
