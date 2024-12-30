import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ad } from '../../Interface/interfaces';

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
   * Fetch a single WikiRecord by its ID.
   * @param id - The ID of the record to fetch.
   */
    getRecordById(id: string): Observable<any> {
      return this.http.get(`${this.apiUrl}${id}`);
    }

  /**
   * Fetch a paginated list of WikiRecords with optional filters.
   * @param filters - An object containing optional filters: content, author, tags.
   * @param page - The page number (default is 0).
   * @param size - The number of records per page (default is 10).
   */
  getRecords(filters: { content?: string; author?: string; tags?: string[] } = {}, page: number = 0, size: number = 10): Observable<any> {
    let params = new HttpParams().set('page', page).set('size', size);

    if (filters.content) params = params.set('content', filters.content);
    if (filters.author) params = params.set('author', filters.author);
    if (filters.tags && filters.tags.length > 0) params = params.set('tags', filters.tags.join(','));

    return this.http.get(`${this.apiUrl}`, { params });
  }

  /**
   * Get the total count of WikiRecords matching optional filters.
   * @param filters - An object containing optional filters: content, author, tags.
   */
  getRecordCount(filters: { content?: string; author?: string; tags?: string[] } = {}): Observable<number> {
    let params = new HttpParams();

    if (filters.content) params = params.set('content', filters.content);
    if (filters.author) params = params.set('author', filters.author);
    if (filters.tags && filters.tags.length > 0) params = params.set('tags', filters.tags.join(','));

    return this.http.get<number>(`${this.apiUrl}count`, { params });
  }

    /**
   * Get a random ad.
   */
    getRandomAd(): Observable<Ad> {
      return this.http.get<Ad>(`${this.apiUrl}random-ad`);
    }
}
