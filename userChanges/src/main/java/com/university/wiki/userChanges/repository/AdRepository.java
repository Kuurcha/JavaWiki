package com.university.wiki.userChanges.repository;

import com.university.wiki.userChanges.entity.Ad;
import com.university.wiki.userChanges.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdRepository  extends JpaRepository<Ad, Long> {
    @Query(value = "SELECT * FROM ad ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Ad getRandomAd();
}
