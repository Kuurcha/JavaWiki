import { Component } from '@angular/core';
import { ContentService } from '../../serivces/content/content.service';
import { CommonModule } from '@angular/common';
import { Ad } from '../../Interface/interfaces';

@Component({
  selector: 'app-ad',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ad.component.html',
  styleUrl: './ad.component.scss'
})
export class AdComponent {
  randomAd: Ad | null = null; 

  constructor(private contentService: ContentService) {}

  ngOnInit(): void {
    this.contentService.getRandomAd().subscribe({
      next: (ad: Ad) => {
        this.randomAd = ad;  
      },
      error: (err) => {
        console.error('Error fetching random ad:', err);
      }
    });
  }
}
