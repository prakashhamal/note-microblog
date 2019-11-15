import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {SharedModuleModule} from "../shared-module/shared-module.module";
import {FormsModule} from "@angular/forms";
import {AuthService} from "./services/auth.service";
import { TestComponent } from './test/test.component';

const routes: Routes = [
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'register', component: RegisterComponent
  },
  {
    path: 'test', component: TestComponent
  },
];

@NgModule({
  imports: [
    SharedModuleModule,
    FormsModule,
    RouterModule.forRoot(
      routes,
      { } // <-- debugging purposes only
    )

  ],
  exports: [
  ],
  declarations: [LoginComponent, RegisterComponent, TestComponent],
  providers: [AuthService]
})
export class AuthModule { }
