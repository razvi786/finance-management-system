import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { AllRequestsComponent } from './requests/all-requests/all-requests.component';
import { DataTablesModule } from 'angular-datatables';
import { RaiseRequestComponent } from './requests/raise-request/raise-request.component';
import { UpdateRequestComponent } from './requests/update-request/update-request.component';
import { ViewRequestComponent } from './requests/view-request/view-request.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { CreateProjectComponent } from './projects/create-project/create-project.component';
import { UpdateProjectComponent } from './projects/update-project/update-project.component';
import { ViewProjectComponent } from './projects/view-project/view-project.component';
import { AllProjectsComponent } from './projects/all-projects/all-projects.component';
import { AllUsersComponent } from './user/all-users/all-users.component';
import { AddUserComponent } from './user/add-user/add-user.component';
import { UpdateUserComponent } from './user/update-user/update-user.component';
import { ViewUserComponent } from './user/view-user/view-user.component';
import { AllVendorsComponent } from './vendors/all-vendors/all-vendors.component';
import { CreateVendorComponent } from './vendors/create-vendor/create-vendor.component';
import { UpdateVendorComponent } from './vendors/update-vendor/update-vendor.component';
import { ViewVendorComponent } from './vendors/view-vendor/view-vendor.component';
import { DatePipe } from '@angular/common';
import { LogoutComponent } from './logout/logout.component';
import { LoggingInterceptor } from './interceptors/logging.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    AllRequestsComponent,
    RaiseRequestComponent,
    UpdateRequestComponent,
    ViewRequestComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    CreateProjectComponent,
    UpdateProjectComponent,
    ViewProjectComponent,
    AllProjectsComponent,
    AllUsersComponent,
    AddUserComponent,
    UpdateUserComponent,
    ViewUserComponent,
    AllVendorsComponent,
    CreateVendorComponent,
    UpdateVendorComponent,
    ViewVendorComponent,
    LogoutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatDialogModule,
    MatTableModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    DataTablesModule,
    MatDatepickerModule,
    MatTooltipModule,
  ],
  providers: [DatePipe, LoggingInterceptor],
  bootstrap: [AppComponent],
})
export class AppModule {}
