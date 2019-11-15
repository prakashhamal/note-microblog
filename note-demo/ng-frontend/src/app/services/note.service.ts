import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders} from '@angular/common/http';
import {Observable} from "rxjs/Rx";
import {environment} from "../../environments/environment";
import {Note} from "../model/note";


@Injectable()
export class NoteService {
  private apiKey = '';
  private httpOptions;

  constructor(private http: HttpClient) {
    this.apiKey = localStorage.getItem("API_KEY");
    console.log(localStorage.getItem("token"));
    let token = JSON.parse(localStorage.getItem('token'));
    if(token) {
      this.httpOptions = {
        headers: new HttpHeaders({
                                   'Content-Type': 'application/json',
                                   'Authorization': `Bearer ${token.access_token}`
                                 })
      };
    }

  }

  public setTokenFromLocal():void {
    console.log(localStorage.getItem("token"));
    let token = JSON.parse(localStorage.getItem('token'));
    if(token) {
      this.httpOptions = {
        headers: new HttpHeaders({
                                   'Content-Type': 'application/json',
                                   'Authorization': `Bearer ${token.access_token}`
                                 })
      };
    }
  }

  /*
  private getHttpOptions():any{
     let token = JSON.parse(localStorage.getItem('token'));
     this.httpHeaders =  {
       headers: new HttpHeaders({
                                  'Content-Type': 'application/json',
                                  'Authorization': `Bearer ${token.access_token}`
                                })
     };
  }
  */

  private authHeaders():any {
    let headers = new HttpHeaders();
    headers.append('Authorization', this.apiKey);
    return headers;
  }

  public getDashboardHashtags(): Observable<string[]>{
    return this.http.get<string[]>(`${environment.serverUrl}/dashboardhashtags`, <Object>this.httpOptions);
  }

  public getAllHashtags(): Observable<string[]>{
    return this.http.get<string[]>(`${environment.serverUrl}/hashtag/all`, <Object>this.httpOptions);
  }



  public getAllNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.serverUrl}/notes/`, <Object>this.httpOptions);
  }

  public getHashtagNotes(hashtag:string): Observable<Note[]> {
    console.log(`${environment.serverUrl}/note/hashtagnotes/${hashtag}`);
    return this.http.get<Note[]>(`${environment.serverUrl}/note/hashtagnotes/${hashtag}`, <Object>this.httpOptions);
  }

  public getBlogPosts(): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.serverUrl}/blog`, <Object>this.httpOptions);
  }

  public getRecentNotes(limit,offset): Observable<Note[]> {
    return this.http.get<Note[]>(`${environment.serverUrl}/note/recentnotes?limit=${limit}&offset=${offset}`, <Object>this.httpOptions);
  }


  deleteNote(note:Note): Observable<Object> {
    return this.http.delete(`${environment.serverUrl}/note/${note.id}`, <Object>this.httpOptions);
  }


  getNoteById(id): Observable<Note>{
    return this.http.get<Note>(`${environment.serverUrl}/note/${id}`, <Object>this.httpOptions);
  }

  saveNote(note: Note): Observable<any>{
    return this.http.post(`${environment.serverUrl}/note/save`, note, <Object>this.httpOptions);
  }

  searchNotes(searchPayload): Observable<any>{
    return this.http.post(`${environment.serverUrl}/note/search`, searchPayload, <Object>this.httpOptions);
  }

  uploadFiles(files):Observable<any>{
    console.log(localStorage.getItem("token"));
    let token = JSON.parse(localStorage.getItem('token'));
    if(token) {
      let httpOptions1 = {
        headers: new HttpHeaders({
                                   'Authorization': `Bearer ${token.access_token}`
                                 })
      };
      return this.http.post(`${environment.serverUrl}/note/uploadfile`, files, <Object>httpOptions1);
    }
    return null;

  }


  lookupHashtags(searchStr: string):Observable<string[]>{
    return this.http.get<string[]>(`${environment.serverUrl}/hashtag/lookup/${searchStr}`, <Object>this.httpOptions);
  }




  private handleError(err: HttpErrorResponse) {
    // in a real world app, we may send the server to some remote logging infrastructure
    // instead of just logging it to the console
    let errorMessage = '';
    if (err.error instanceof Error) {
      // A client-side or network error occurred. Handle it accordingly.
      errorMessage = `An error occurred: ${err.error.message}`;
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
    }
    console.error(errorMessage);
    return Observable.throw(errorMessage);
  }
}
