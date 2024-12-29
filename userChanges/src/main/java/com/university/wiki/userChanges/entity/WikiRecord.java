package com.university.wiki.userChanges.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wiki_records")
public class WikiRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_id_seq")
    @SequenceGenerator(name = "doc_id_seq", sequenceName = "doc_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, length = 255)
    private String header;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany
    @JoinTable(
            name = "wiki_record_tags",
            joinColumns = @JoinColumn(name = "wiki_record_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();



}
