import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  formLogin! : FormGroup;
  constructor() {
    this.initialize();
  }

  ngOnInit(): void {
  }

  initialize() {
    this.formLogin = new FormGroup({
      email : new FormControl('', [Validators.required, Validators.email]),
      password : new FormControl('', Validators.required)
    })
  }

  login() {
    console.log(this.formLogin);

  }

}
