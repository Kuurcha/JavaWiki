package com.university.wiki.userChanges.service;

import com.university.wiki.userChanges.entity.Ad;
import com.university.wiki.userChanges.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdService {
    private final AdRepository adRepository;

    @Autowired
    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    public Ad getRandomAd() {
        return adRepository.getRandomAd();
    }

    public Ad getAdById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found with ID: " + id));
    }

    public Ad insertAd(Ad ad) {
        return adRepository.save(ad);
    }
}
