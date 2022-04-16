import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { LoginService } from '../service/login.service';


@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  public messageFromServer = '';


  constructor(private http: HttpClient, public loginService: LoginService) {
  }

  ngOnInit(): void {
  }






}
