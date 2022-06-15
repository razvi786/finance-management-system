import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { RegisterComponent } from './register/register.component';
import { AllRequestsComponent } from './requests/all-requests/all-requests.component';
import { RaiseRequestComponent } from './requests/raise-request/raise-request.component';
import { UpdateRequestComponent } from './requests/update-request/update-request.component';
import { ViewRequestComponent } from './requests/view-request/view-request.component';
import { AuthenticationGuard } from './services/authentication.guard';
import { AllVendorsComponent } from './vendors/all-vendors/all-vendors.component';
import { CreateVendorComponent } from './vendors/create-vendor/create-vendor.component';
import { UpdateVendorComponent } from './vendors/update-vendor/update-vendor.component';
import { ViewVendorComponent } from './vendors/view-vendor/view-vendor.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'logout',
    component: LogoutComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'requests/add',
    component: RaiseRequestComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'requests/edit/:request_uuid',
    component: UpdateRequestComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'requests/view/:request_uuid',
    component: ViewRequestComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'requests',
    component: AllRequestsComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'vendors/add',
    component: CreateVendorComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'vendors/edit/:vendor_id',
    component: UpdateVendorComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'vendors/view/:vendor_id',
    component: ViewVendorComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'vendors',
    component: AllVendorsComponent,
    canActivate: [AuthenticationGuard],
  },
  { path: '**', component: HomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
