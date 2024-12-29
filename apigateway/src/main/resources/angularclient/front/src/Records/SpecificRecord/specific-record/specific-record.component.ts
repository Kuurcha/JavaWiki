import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ContentService } from '../../../serivces/content/content.service';
import { RecordViewerComponent } from '../../RecordViewer/record-viewer/record-viewer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-specific-record',
  standalone: true,
  imports: [RecordViewerComponent, CommonModule],
  templateUrl: './specific-record.component.html',
  styleUrl: './specific-record.component.scss'
})
export class SpecificRecordComponent {
  id: string = '';
  record: any = {};
  
  constructor(private route: ActivatedRoute, private contentService: ContentService) {}

  ngOnInit(): void {
   
    this.route.queryParams.subscribe(params => {
      this.id = params['id']; 
      if (this.id) {
        this.fetchRecord(this.id);
      }
    });
  }


  fetchRecord(id: string): void {
    this.contentService.getRecordById(id).subscribe({
      next: (data) => {
        this.record = data;  
      },
      error: (err) => {
        console.error('Error fetching record:', err);
      }
    });
  }
  }


