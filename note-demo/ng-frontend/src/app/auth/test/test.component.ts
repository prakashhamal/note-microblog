import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  constructor(private http: HttpClient, private authService: AuthService) { }

  ngOnInit() {
  }

  punchOut(vendor):void{

  }
}
