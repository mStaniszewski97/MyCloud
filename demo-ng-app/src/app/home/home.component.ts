import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit() {
    // this.http.get('http://localhost:8082/isLogged').subscribe(data => {
    //   if (data === true) {
    //     document.getElementById('loginButton').hidden = true;
    //     document.getElementById('driveButton').hidden = false;
    //     document.getElementById('logoutButton').hidden = false;
    //   }
    //   console.log(data);
    // });
  }

}
