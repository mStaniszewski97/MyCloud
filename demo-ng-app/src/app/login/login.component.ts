import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: LoginModel = {
    email: '',
    password: '',
  };

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get('http://localhost:8082/isLogged').subscribe(data => {
      if (data === false) {
        console.log('falsz');
      } else {
        console.log('prawda');
      }
      console.log(data);
    });
  }

  login(): void {
    const url = 'http://localhost:8082/login';
    this.http.post(url, this.model).subscribe(
      res => {
        window.location.pathname = '';
      },
      err => {
        alert('Login failed :(\nBad email or password!!!');
      }
    );
  }

}

export interface LoginModel {
  email: string;
  password: string;
}
