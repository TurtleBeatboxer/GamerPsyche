import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-aiform',
  templateUrl: './aiform.component.html',
  styleUrls: ['./aiform.component.css']
})
export class AIFormComponent implements OnInit {
  dataForm: FormGroup;
  constructor(private fb: FormBuilder, private http: HttpClient) { }

  ngOnInit(): void {
    this.dataForm = this.fb.group({
      username: ['', Validators.required],
    })
  }

  getData(){
    this.http.post(`http://localhost:8080/AI/data`,  this.dataForm.value.username).subscribe((x)=>{
      console.log(x)
    })
  }

}
