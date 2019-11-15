import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import { ActivatedRoute } from '@angular/router';
import {NoteService} from "../services/note.service";
import {Note} from "../model/note";
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { ChangeEvent } from '@ckeditor/ckeditor5-angular/ckeditor.component';

@Component({
             selector: 'app-note',
             templateUrl: './note.component.html',
             styleUrls: ['./note.component.css']
           })
export class NoteComponent implements OnInit
{
  public Editor = ClassicEditor;
  note: Note;
  hashtags: string[];
  allNotes: {};
  notes: Note[];
  displayForm: boolean = false;
  searchString: string = '';
  viewMore: boolean = false;
  notesPage: number = 1;
  quickAdd:boolean = true;
  keywords: string[];
  inProgress:boolean = false;
  selectedNoteIndex: number = -1;
  files:  File[];
  hashtagLookupStr: string = '';
  model = {
    editorData: ''
  };

  constructor(private http: HttpClient,private route: ActivatedRoute, private noteService: NoteService)
  {
    this.route.queryParams.subscribe(map => {
      if('apiKey' in map){
        localStorage.setItem("API_KEY",map['apiKey']);
      }
    });
    /*
    const apiKey: string = this.route.snapshot.queryParamMap.get('apiKey');
    if(apiKey) {
      localStorage.setItem("API_KEY", apiKey);
    }
    */
    this.notes = Array<Note>();
    this.keywords = Array<string>();
    this.hashtags = Array<string>();
    this.files = Array<File>();
    this.initNote();
    this.loadHashtags();
    this.loadRecentNotes(20,0);


  }

  loadRecentNotes(limit,offset): void
  {

    this.noteService.getRecentNotes(limit,offset).subscribe(data => {
      data.forEach(note=>{
        note.descJSON = JSON.parse(note.description)
        this.notes.push(note);
      });
    });
  }

  initNote(){
    this.note = new Note('',false);
    this.selectedNoteIndex = -1;
    this.files = [];
  }

  ngOnInit()
  {

  }

  loadHashtags(): void
  {
    this.noteService.getAllHashtags().subscribe(data => {
      this.hashtags = data;
    });
  }

  addKeyword(hashtag): void{
    if(this.keywords.indexOf(hashtag) < 0) {
      this.keywords.push(hashtag);
    }
    this.searchWithKeywords(true);

  }

  removeKeyword(keyword): void{
    this.keywords.splice(this.keywords.indexOf(keyword), 1);
    this.searchWithKeywords(true);
  }

  add()
  {
    this.initNote();
    this.quickAdd = false;
  }

  saveQuickAddNote(): void {
    this.noteService.saveNote(this.note).subscribe(data => {
      if(this.selectedNoteIndex < 0) {
        this.notes.unshift(data);
      }else{
        this.notes[this.selectedNoteIndex] = data;
      }
      this.initNote();
    });
  }

  saveNote(): void {
    this.inProgress = true;
    this.note.title = this.model.editorData;
    console.log("Save note and upload files");
    this.noteService.saveNote(this.note).subscribe(data => {
      console.log('Note has been successfully saved');
      console.log(data);
      this.uploadFiles(data.id);
      if(this.selectedNoteIndex < 0) {
        this.notes.unshift(data);
      }else{
        this.notes[this.selectedNoteIndex] = data;
      }
      this.inProgress = false;
    });
  }

  uploadFiles(noteId): void {
    if(this.files.length > 0) {
      let formData = new FormData();
      formData.append("noteId", noteId);
      this.files.forEach((item, index) => {
        formData.append("files", item);
      });

      console.log(formData);
      this.noteService.uploadFiles(formData).subscribe(data => {
        console.log("Saved files");
      });
    }
  }

  cancel()
  {
    this.initNote();
    this.quickAdd = true;
    this.selectedNoteIndex = -1;
  }

  selectNote(note: Note, index)
  {
    this.getNoteById(note.id);
    this.quickAdd = false;
    this.selectedNoteIndex = index;
  }

  getAllNotes(): void
  {
    this.noteService.getAllNotes().subscribe(data => {
      this.allNotes = data;
    });
  }

  getHashtagNotes(hashTag): void
  {
    this.notes = []
    this.inProgress = true;
    this.noteService.getHashtagNotes(hashTag).subscribe(data => {
      this.inProgress = false;
      data.forEach(note=>{
         note.descJSON = JSON.parse(note.description)
         this.notes.push(note)
      });
    });
  }

  getNoteById(id)
  {
    this.noteService.getNoteById(id).subscribe(data => {
      this.note = data;
      this.model.editorData = this.note.title;

    });
  }

  deleteNote(note: Note, index)
  {
    this.noteService.deleteNote(note).subscribe(data => {
      this.notes.splice(index,1);
    });
  }

  archiveNote(note: Note, index)
  {
    note.archived = true;

    console.log(note);
    this.noteService.saveNote(note).subscribe(data => {
      this.notes.splice(index,1);
    });
  }

  onSubmit(): void
  {
    this.note.archived = false;
    console.log("Adding new note");
    this.noteService.saveNote(this.note).subscribe(data => {
      this.getAllNotes();
    });
  }

  search(): void
  {
    if(this.searchString.length > 0) {
      let segments = this.searchString.split(" ");
      for (let segment of segments) {
          this.addKeyword(segment.trim());
      }
    }
    this.searchWithKeywords(true);
  }



  searchWithKeywords(refresh = false){
    this.inProgress = true;
    this.quickAdd = true;
    let searchStr = this.keywords.join(" ");
    var searchPayload = {"searchString": searchStr, "offset": 0,"limit":10};
    this.noteService.searchNotes(searchPayload).subscribe(response => {

      this.inProgress = false;
      if(refresh) {
        this.notes = new Array<Note>();
        this.notesPage = 1;
      }
      for(let item of response.result){
        this.notes.push(item);
      }
    });
  }


  loadMoreNotes(searchStr): void
  {
    this.notesPage++;
    console.log(this.notesPage);
    this.loadRecentNotes(20,this.notesPage*20);
  }

  clearSearch(): void
  {
    this.getAllNotes();
    this.loadHashtags();
    this.searchString = "";
  }

  autoGrowTextZone(e) {
    e.target.style.height = "0px";
    e.target.style.height = (e.target.scrollHeight + 25)+"px";
  }

  clickedBody(){
    console.log('khai ta');
  }

  public fileEvent($event) {
    const fileSelected: File = $event.target.files[0];
    this.files.push(fileSelected);
  }

  public removeFile(index){
    this.files.splice(index,1);
  }

  public hashtagLookup(){
    console.log(this.hashtagLookupStr);

    if(this.hashtagLookupStr.trim().length > 0) {
      this.noteService.lookupHashtags(this.hashtagLookupStr).subscribe(data => {
        console.log(data);
      });
    }

  }
}
