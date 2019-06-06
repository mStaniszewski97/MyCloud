import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-contact-page',
  templateUrl: './contact-page.component.html',
  styleUrls: ['./contact-page.component.css']
})
export class ContactPageComponent implements OnInit {
  model: FeedbackViewModel = {
    name: '',
    email: '',
    feedback: ''
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
  }

  sendQuestion(): void {
    const url = 'http://localhost:8082/feedback';
    this.http.post(url, this.model).subscribe(
      res => {
        console.log('Question was send!');
        location.reload();
      },
      err => {
        alert('Question was not send :(');
      }
    );
  }

}

export interface FeedbackViewModel {
  name: string;
  email: string;
  feedback: string;
}
