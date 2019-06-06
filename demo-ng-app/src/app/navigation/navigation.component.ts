import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get('http://localhost:8082/isLogged').subscribe(data => {
      if (data === true) {
        document.getElementById('loginButton').hidden = true;
        document.getElementById('driveButton').hidden = false;
        document.getElementById('logoutButton').hidden = false;
      }
      console.log(data);
    });
  }

  logout(): void {
    this.http.get('http://localhost:8082/logout').subscribe();
    document.getElementById('loginButton').hidden = false;
    document.getElementById('logoutButton').hidden = true;
    document.getElementById('driveButton').hidden = true;
    window.location.pathname = '';
  }
}
