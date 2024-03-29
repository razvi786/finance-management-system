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
import { AssignApproverLevelComponent } from './approver-level/assign-approver-level/assign-approver-level.component';
import { FormsModule } from '@angular/forms';
import { AllPaymentsComponent } from './payments/all-payments/all-payments.component';
import { ViewPaymentComponent } from './payments/view-payment/view-payment.component';
import { MdbAccordionModule } from 'mdb-angular-ui-kit/accordion';
import { MdbCarouselModule } from 'mdb-angular-ui-kit/carousel';
import { MdbCheckboxModule } from 'mdb-angular-ui-kit/checkbox';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { MdbDropdownModule } from 'mdb-angular-ui-kit/dropdown';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbModalModule } from 'mdb-angular-ui-kit/modal';
import { MdbPopoverModule } from 'mdb-angular-ui-kit/popover';
import { MdbRadioModule } from 'mdb-angular-ui-kit/radio';
import { MdbRangeModule } from 'mdb-angular-ui-kit/range';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { MdbScrollspyModule } from 'mdb-angular-ui-kit/scrollspy';
import { MdbTabsModule } from 'mdb-angular-ui-kit/tabs';
import { MdbTooltipModule } from 'mdb-angular-ui-kit/tooltip';
import { MdbValidationModule } from 'mdb-angular-ui-kit/validation';

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
    AssignApproverLevelComponent,
    AllPaymentsComponent,
    ViewPaymentComponent,
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
    FormsModule,
    MdbAccordionModule,
    MdbCarouselModule,
    MdbCheckboxModule,
    MdbCollapseModule,
    MdbDropdownModule,
    MdbFormsModule,
    MdbModalModule,
    MdbPopoverModule,
    MdbRadioModule,
    MdbRangeModule,
    MdbRippleModule,
    MdbScrollspyModule,
    MdbTabsModule,
    MdbTooltipModule,
    MdbValidationModule,
  ],
  providers: [DatePipe, LoggingInterceptor],
  bootstrap: [AppComponent],
})
export class AppModule {}
