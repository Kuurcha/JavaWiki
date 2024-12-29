import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Editor, NgxEditorModule, Toolbar, Validators } from 'ngx-editor';
import { ContentService } from '../../serivces/content/content.service';
import { AuthService } from '../../serivces/auth/auth.service';
import { RecordViewerComponent } from "../RecordViewer/record-viewer/record-viewer.component";


interface Tag {
  id: string;
  name: string;
}

@Component({
  selector: 'app-editor',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgxEditorModule, FormsModule, RecordViewerComponent],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.scss'
})
export class EditorComponent implements OnDestroy {
  editor: Editor;
  editorForm =  new FormGroup({      
    html: new FormControl(''),
    tagsInput: new FormControl('', [Validators.maxLength(100)]),
    header: new FormControl('', [Validators.maxLength(100), Validators.required()])
  })
  showPreview = true;
  toolbar: Toolbar = [
    ['bold', 'italic', 'underline', 'strike'],
    ['code', 'blockquote'],
    ['link', 'image'],
    ['undo', 'redo'],
    ['align_left', 'align_center', 'align_right', 'align_justify'],
    [{ heading: ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'] }],
    ['horizontal_rule', 'format_clear', 'indent', 'outdent'],
    ['ordered_list', 'bullet_list'],
  ];
  
  constructor(private fb: FormBuilder, private contentService: ContentService, private authService: AuthService){

    this.editor = new Editor();
  }

  activatePreview(){
    // console.log(this.editorForm.controls.html.value);
    // console.log(this.parsedTags)
  }

  get tagsInputControl() {
    return this.editorForm.get('tagsInput');
  }
  get parsedTags(): string[] {
    if (this.editorForm.controls.tagsInput.value == null)
      return [];

    let tags = this.editorForm.controls.tagsInput.value.split(/\s+/) 
      .map(tag => tag.trim())  
      .filter(tag => tag);     

    return tags.slice(0, 5);
  }

  get parsedDisplayTags(): Tag[] {
    if (this.editorForm.controls.tagsInput.value == null)
      return [];
  

    let tags = this.editorForm.controls.tagsInput.value.split(/\s+/)
      .map(tag => tag.trim())  
      .filter(tag => tag);
  

    return tags.slice(0, 5).map((tag, index) => ({
      id: `${index + 1}`,  
      name: tag
    }));
  }
  togglePreview(): void {
    this.showPreview = !this.showPreview; 
  }

  get currentDate(): Date {
    return new Date();
  }


  getDate(){
    return new Date();
  }
  
  getContent(){
    return this.editorForm.value.html ?? "";
  }
  getHeader(){
    return this.editorForm.value.header ?? "";
  }
  getAuthorName(){
    return this.authService.getUsername() ?? "Mysterous Anonymous";
  }

  sendContent(): void {
    let content = this.editorForm.controls.html.value;
    let header = this.editorForm.controls.header.value;
    let tags = this.parsedTags;
    if (!content || !header) {
        alert('Незаполнена статья или заголовок!');
        return;  
    }

    let author = this.authService.getUsername() ?? "Unknown Author"; 


    const contentRequest = {
        content: content,
        author: author,
        header: header,
        tags: tags
    };


    this.contentService.saveContent(contentRequest).subscribe({
      next: (response) => {

          console.log('Content saved successfully', response);
      },
      error: (error) => {

          console.error('Error saving content', error);
          alert('Failed to save content.');
      }
    });
  }
  
  ngOnDestroy(): void {
    this.editor?.destroy();
  }
}
