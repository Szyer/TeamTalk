import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './my-components/auth/login/login.component';
import { RegisterComponent } from './my-components/auth/register/register.component';
import { HomeComponent } from './my-components/home/home.component';
import { PageNotFoundComponent } from './my-components/page-not-found/page-not-found.component';

const routes: Routes = [
  {path:"", redirectTo:"login", pathMatch:"full"},
  {path:"login", component:LoginComponent},
  {path:"register", component:RegisterComponent},
  {path:"home", component:HomeComponent},
  {path:"**", component:PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
