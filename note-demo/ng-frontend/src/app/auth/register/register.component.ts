import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {User} from "../models/user";

@Component({
             selector: 'app-register',
             templateUrl: './register.component.html',
             styleUrls: ['./register.component.css']
           })
export class RegisterComponent implements OnInit
{
  user: User;

  constructor(private authService: AuthService)
  {
    this.user = new User();
  }

  ngOnInit()
  {
  }


  onSubmit() {
    console.log(this.user);

  }

}
