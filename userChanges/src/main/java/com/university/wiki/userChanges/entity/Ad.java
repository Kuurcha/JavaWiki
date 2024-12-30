package com.university.wiki.userChanges.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ad_id_seq")
    @SequenceGenerator(name = "ad_id_seq", sequenceName = "ad_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String imageLink;
}
