import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {AuthModule} from './auth/auth.module';
import {RouterModule, Routes} from '@angular/router';
import { HomeComponent } from './home/home.component';
import {SharedModuleModule} from "./shared-module/shared-module.module";
import { NoteComponent } from './note/note.component';
import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { BlogComponent } from './blog/blog.component';
import {NoteService} from "./services/note.service";
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import {TokenInterceptorService} from "./services/token-iterceptor.service";

const routes: Routes = [
  { path: '', component: BlogComponent },
  { path: 'dashboard', component: NoteComponent },
  { path: 'blog', component: BlogComponent }
];
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NoteComponent,
    BlogComponent
  ],
  imports: [
    RouterModule.forRoot(
      routes,
      { enableTracing: true } // <-- debugging purposes only
    ),
    RouterModule,
    BrowserModule,
    AuthModule,
    HttpClientModule,
    FormsModule,
    SharedModuleModule,
    CKEditorModule
  ],
  providers: [NoteService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
