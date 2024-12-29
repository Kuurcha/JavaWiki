import { Component } from '@angular/core';
import { RecordListComponent } from '../../Records/RecordList/record-list/record-list.component';

@Component({
  selector: 'app-main-page',
  standalone: true,
  imports: [RecordListComponent],
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.scss'
})
export class MainPageComponent {

}
