import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{6,}$/;


  constructor() {
    this.initializeForm();
  }

  initializeForm() {
    this.registerForm = new FormGroup({
      firstname: new FormControl('', Validators.required),
      lastname: new FormControl('', [Validators.required]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6), Validators.pattern(this.passwordPattern)]),
      conf_password: new FormControl('', [Validators.required,
      this.matchValues('password')])
    })
  }

  ngOnInit(): void {

  }

  register() {
    console.log(this.registerForm);
  }

  matchValues(matchTo: string): ValidatorFn {
    return (control: AbstractControl) => {
      if (control.parent && control.parent instanceof FormGroup) {
        return control.value === control.parent.controls[matchTo].value ? null : { isMatching: true };
      }
      return null;
    }
  }

}
