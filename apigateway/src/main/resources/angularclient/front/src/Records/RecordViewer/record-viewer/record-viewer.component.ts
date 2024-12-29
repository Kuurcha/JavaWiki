import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';


interface Tag {
  id: string;
  name: string;
}

@Component({
  selector: 'app-record-viewer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './record-viewer.component.html',
  styleUrl: './record-viewer.component.scss'
})
export class RecordViewerComponent {

  @Input() id: string = '';
  @Input() header: string = '';
  @Input() htmlContent: string = '';
  @Input() authorName: string = '';
  @Input() tags: Tag[] = [];
  @Input() showPreview: boolean = false;
  @Input() date: Date = new Date();
  @Input() truncated: boolean = false;
  @Input() truncateLength: number = 300;
  
  constructor(private router: Router) {} 
  
  get truncatedContent(): string {
    return this.htmlContent.length > this.truncateLength
      ? this.htmlContent.substring(0, this.truncateLength) + '...'
      : this.htmlContent;
  }

  goToSinglePage(id: string): void {
    this.router.navigate(['/page'], { queryParams: { id: id } });
  }
}
