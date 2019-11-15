import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import {User} from "../models/user";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";
import {NoteService} from "../../services/note.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  submitted = false;
  user: User;
  loginError:string = '';
  constructor(private authService: AuthService,private router: Router, private noteService: NoteService)
  {
    this.user = new User();
    //console.log(btoa('baeldung-admin' : 'baeldung'))
  }

  ngOnInit() {
  }

  punchOut():void{
    this.authService.punchOut();
  }

  onSubmit() {
    this.loginError = '';
    this.submitted = true;
    console.log(this.user);
    localStorage.setItem('token','');
    this.authService.login(this.user).subscribe(data=>{
      if(data && data.access_token) {
        console.log(data);
        localStorage.setItem('token', JSON.stringify(data));
        console.log("Access Token : " + localStorage.getItem('token'));
        this.noteService.setTokenFromLocal();
        this.router.navigate(['/', 'dashboard']);
      }else{
        this.loginError = "Failed login";
      }
    },error=>{
      this.loginError = "Invalid login credentials";
    });
  }


}
