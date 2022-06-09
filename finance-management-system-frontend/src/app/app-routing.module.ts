import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AllRequestsComponent } from './requests/all-requests/all-requests.component';
import { RaiseRequestComponent } from './requests/raise-request/raise-request.component';
import { UpdateRequestComponent } from './requests/update-request/update-request.component';
import { ViewRequestComponent } from './requests/view-request/view-request.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent },
  { path: 'requests/add', component: RaiseRequestComponent },
  { path: 'requests/edit/:request_uuid', component: UpdateRequestComponent },
  { path: 'requests/view/:request_uuid', component: ViewRequestComponent },
  { path: 'requests', component: AllRequestsComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent},
  { path: 'reset-password', component:ResetPasswordComponent},
  { path: '**', component: HomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
