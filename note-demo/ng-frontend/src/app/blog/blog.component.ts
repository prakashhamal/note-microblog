import { Component, OnInit } from '@angular/core';
import {NoteService} from "../services/note.service";
import {Note} from "../model/note";

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {

  blognotes: Note[];

  constructor(private noteService: NoteService) {
    console.log("Constructor bhitra");
     this.blognotes = Array<Note>();
     this.loadBlogPosts();
  }

  ngOnInit() {
     console.log("Init");
  }

  loadBlogPosts(){
    console.log("Get blog posts");
    this.noteService.getBlogPosts().subscribe(
      value=>{
        console.log(value);
        this.blognotes = value;
        console.log(this.blognotes);
      },
      error => {
        console.log(error);
        // error is an error object
      });
  }



}
