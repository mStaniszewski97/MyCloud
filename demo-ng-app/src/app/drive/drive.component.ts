import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-drive',
  templateUrl: './drive.component.html',
  styleUrls: ['./drive.component.css']
})
export class DriveComponent implements OnInit {

  myfiles: any;

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.http.get('http://localhost:8082/files').subscribe((res) => {
      this.myfiles = res;
      console.log(this.myfiles[0].key);
      console.log(this.myfiles[0].lastModified);
  });
  }

  saveFile(files: FileList) {
    const fileToUpload = files.item(0);
    const formData = new FormData();
    formData.append('file', fileToUpload, fileToUpload.name);
    this.http.post('http://localhost:8082/storeFile', formData).subscribe((val) => {
      console.log('file: ' + fileToUpload.name + 'was upload to server');
      window.location.reload();
    });
  }

  delete(fileKey: string) {
    console.log('deleting file: ' + fileKey);
    const formData = new FormData();
    formData.append('fileKey', fileKey);
    this.http.post('http://localhost:8082/deleteFile', formData).subscribe(
      res => {
        window.location.reload();
      },
      err => {
        alert('File was not delete :(');
      }
    );
  }

  download(fileKey: string) {
    console.log('downloading of file : ' + fileKey + ' is started');
    window.location.href = 'http://localhost:8082/downloadFile/' + fileKey;
  }
}

export class MyFiles {
  key: string;
  lastModified: string;
  size: number;
}
