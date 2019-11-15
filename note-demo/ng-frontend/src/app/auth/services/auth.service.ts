import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from "../models/user";
import {Observable} from "rxjs/Rx";
import {environment} from "../../../environments/environment";

@Injectable()
export class AuthService {
  private httpOptions = {};
  constructor(private http: HttpClient) {
    //btoa('username:password')

  }

  public punchOut():void{
    console.log("Punching out to the supplier site. ");
  }

  public login(user:User):Observable<any>{
    var payload = JSON.stringify({'username':user.user_name,'password':user.password});
    this.httpOptions =  {
      headers: new HttpHeaders({
                                 'Authorization': 'Basic '+btoa(`${user.user_name}:${user.password}`)
                               })
    };

    return this.http.post(`${environment.serverUrl}/oauth/token?grant_type=client_credentials`,{},this.httpOptions);
  }
}
