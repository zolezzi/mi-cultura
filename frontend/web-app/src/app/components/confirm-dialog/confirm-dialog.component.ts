import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.css'],
})
export class ConfirmDialogComponent implements OnInit {
  message: string;
  title: string;
  btn = 'aceptar';
  constructor(
   
    private dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
   
    this.message = data.message;
    this.title = data.title;
  }

  ngOnInit(): void {
 
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
