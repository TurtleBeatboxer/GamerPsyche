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
    this.http.post<{username: string, championId: number}>(`http://localhost:8080/AI/data`, {username: this.dataForm.value.username, championId: this.dataForm.value.championId}).subscribe((x)=>{
      console.log(x)
    })
  }

  test(){
    this.http.get("http://localhost:8080/AI/data/winRate/byChampion").subscribe((x)=>{
      console.log(x)
    })
  }
}
