package com.university.wiki.userChanges;


import com.university.wiki.userChanges.entity.Ad;
import com.university.wiki.userChanges.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
@RestController
public class AdController {

    @Autowired
    private AdService adService;


    @GetMapping("/random-ad")
    public Ad getRandomAd() {
        return adService.getRandomAd();
    }
}