import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  MatAutocompleteModule,
  MatButtonModule, MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule, MatChipsModule, MatDatepickerModule, MatDialogModule, MatExpansionModule, MatGridListModule,
  MatIconModule,
  MatInputModule, MatListModule,
  MatMenuModule, MatNativeDateModule,
  MatProgressBarModule, MatProgressSpinnerModule,
  MatRadioModule,
  MatSelectModule, MatSidenavModule, MatSlideToggleModule, MatSnackBarModule, MatTableModule, MatTabsModule,
  MatTooltipModule
} from '@angular/material';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

@NgModule({
            imports: [
              CommonModule,
              HttpClientModule,
              FormsModule
            ],
            declarations: [],
            exports: [

              // MATERIAL MODULES
              BrowserAnimationsModule,
              MatAutocompleteModule,
              MatInputModule,
              MatButtonModule,
              MatTooltipModule,
              MatMenuModule,
              MatDialogModule,
              MatIconModule,
              MatCardModule,
              MatChipsModule,
              MatCheckboxModule,
              MatRadioModule,
              MatProgressBarModule,
              MatSelectModule,
              MatProgressSpinnerModule,
              MatProgressBarModule,
              MatTabsModule,
              MatListModule,
              MatButtonToggleModule,
              MatSnackBarModule,
              MatSidenavModule,
              MatExpansionModule,
              MatTableModule,
              MatSlideToggleModule,
              MatDatepickerModule,
              MatNativeDateModule,
              MatGridListModule,
            ]
          })
export class SharedModuleModule
{
}
