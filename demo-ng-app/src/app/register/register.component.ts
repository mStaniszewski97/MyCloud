import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  model: User = {
    username: '',
    email: '',
    password: ''
  };

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  register(): void{
    const url = 'http://localhost:8082/register';
    this.http.post(url, this.model).subscribe(
      res => {
        window.location.pathname = '';
      },
      err => {
        alert('Registration failed :( \nTry again!');
      }
    );
  }

}

export interface User {
  username: string;
  email: string;
  password: string;
}
