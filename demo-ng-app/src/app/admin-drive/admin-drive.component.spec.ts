import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminDriveComponent } from './admin-drive.component';

describe('AdminDriveComponent', () => {
  let component: AdminDriveComponent;
  let fixture: ComponentFixture<AdminDriveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminDriveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminDriveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
