import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { QueryList, ViewChildren } from '@angular/core';
import { Observable } from 'rxjs';
import jsPDF from 'jspdf';
//import * as jsPDF from 'jspdf';
import 'jspdf-autotable';

import html2canvas from 'html2canvas';



import {
  UserService,
} from 'src/app/DemoPages/UserPages/_services/user.service';
import { MasterService } from 'src/app/layout/service';
import { MessageService } from 'primeng/api';


@Component({
  selector: 'app-statushistory',
  templateUrl: './statushistory.component.html',
  styleUrls: ['./statushistory.component.css'],
  providers: [MessageService]
})
export class StatusHistoryComponent implements OnInit {
  selectedRowData: any[];
   user: any = [];
  content:any []= [];
  
@Output()
editStatusEntry = new EventEmitter();
  
  constructor(private masterService: MasterService, private messageService: MessageService) {
    
  }
  ngOnInit():void {

    this.masterService.getAllStatus().subscribe(
      data => {
        
        this.content =JSON.parse(JSON.stringify(data.data));
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  //  this.loadEmployees()
  }
  onTableHeaderCheckboxToggle(event: any) {
  }
  editProduct(product: any) {
    this.editStatusEntry.emit(product);
  }
}

function tableDragger(id: HTMLElement, arg1: { mode: string; onlyBody: boolean; animation: number; }) {
  throw new Error('Function not implemented.');
}
