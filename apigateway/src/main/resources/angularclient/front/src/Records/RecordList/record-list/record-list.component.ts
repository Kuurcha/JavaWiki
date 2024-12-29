import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ContentService } from '../../../serivces/content/content.service';
import { RecordViewerComponent } from '../../RecordViewer/record-viewer/record-viewer.component';

@Component({
  selector: 'app-record-list',
  standalone: true,
  imports: [CommonModule, RecordViewerComponent],
  templateUrl: './record-list.component.html',
  styleUrl: './record-list.component.scss'
})
export class RecordListComponent implements OnInit{
  currentPage: number = 0;
  pageSize: number = 10;
  totalRecords: number = 0;  
  records: any[] = [];
  
  showPreview: boolean = true;
  parsedTags: string[] = []

  filters = {
    content: '',
    author: '',
    tags: [] as string[],
  };
  
  fetchRecords(): void {
    this.contentService
      .getRecords(this.filters, this.currentPage, this.pageSize)
      .subscribe({
        next: (response: any) => {
          this.records = response.content;
        },
        error: (err) => {
          console.error('Error fetching records:', err);
        }
      });
  }

  fetchTotalCount(): void {
    this.contentService
      .getRecordCount(this.filters)
      .subscribe({
        next: (count: number) => {
          this.totalRecords = count;
        },
        error: (err) => {
          console.error('Error fetching total count:', err);
        }
      });
  }

  constructor(private contentService: ContentService) {}

  ngOnInit(): void {
    this.fetchRecords();
    this.fetchTotalCount();
    this.loadPage(this.currentPage); 
    
  }

  loadPage(page: number): void {
    if (page < 0 || (this.totalPages > 0 && page >= this.totalPages)) {
      console.warn("Invalid page index: ", page);
      return;
    }
  
    this.contentService
      .getRecords(this.filters, page, this.pageSize)
      .subscribe({
        next: (response: any) => {
          this.records = response.content || [];

        },
        error: (err) => {
          console.error('Error fetching records:', err);
        }
      });

      this.contentService.getRecordCount(this.filters)
      .subscribe({
        next: (response: any) => {
          this.totalRecords  = response;
  
          
          if (this.totalRecords === 0) {
            this.currentPage = 0; 
            alert("No records found.");
          }
        },
        error: (err) => {
          console.error('Error fetching records:', err);
        }
      });
  }


  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadPage(this.currentPage);
    } else {
      console.warn("No next page available.");
    }
  }
  
  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadPage(this.currentPage);
    } else {
      console.warn("No previous page available.");
    }
  }
  
  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadPage(this.currentPage);
    } else {
      console.warn("Invalid page index: ", page);
    }
  }

  get totalPages(): number {
    return Math.ceil(this.totalRecords / this.pageSize);
  }
}
