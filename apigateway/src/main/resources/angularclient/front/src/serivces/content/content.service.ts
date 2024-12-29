import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContentService {
  private apiUrl = 'http://localhost:4201/api/userChanges/';  // Backend endpoint

  constructor(private http: HttpClient) {}

  /**
   * Save a new WikiRecord.
   * @param contentRequest - The content request object.
   */
  saveContent(contentRequest: { content: string; author: string; header: string; tags: string[] }): Observable<any> {
    return this.http.post(`${this.apiUrl}`, contentRequest);
  }

  /**
   * Fetch a WikiRecord by its ID.
   * @param id - The ID of the WikiRecord.
   */
  getRecordById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  /**
   * Fetch a paginated list of WikiRecords.
   * @param page - The page number.
   * @param size - The number of records per page.
   */
  getRecords(page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get(`${this.apiUrl}`, { params });
  }

  /**
   * Search WikiRecords by content.
   * @param content - The content to search for.
   * @param page - The page number.
   * @param size - The number of records per page.
   */
  searchByContent(content: string, page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('content', content)
      .set('page', page)
      .set('size', size);
    return this.http.get(`${this.apiUrl}/search/content`, { params });
  }

  /**
   * Search WikiRecords by author.
   * @param author - The author to search for.
   * @param page - The page number.
   * @param size - The number of records per page.
   */
  searchByAuthor(author: string, page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('author', author)
      .set('page', page)
      .set('size', size);
    return this.http.get(`${this.apiUrl}/search/author`, { params });
  }

  /**
   * Search WikiRecords by tags.
   * @param tags - The tags to search for.
   * @param page - The page number.
   * @param size - The number of records per page.
   */
  searchByTags(tags: string[], page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('tags', tags.join(','))
      .set('page', page)
      .set('size', size);
    return this.http.get(`${this.apiUrl}/search/tags`, { params });
  }
}
