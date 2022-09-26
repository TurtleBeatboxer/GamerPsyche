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
      championId: [-1, Validators.required]
    })
  }

  getData(){
    this.http.get(`http://localhost:8080/AI/${this.dataForm.value.username}/${this.dataForm.value.championId}`).subscribe((x)=>{
      console.log(x)
    })
  }

}
