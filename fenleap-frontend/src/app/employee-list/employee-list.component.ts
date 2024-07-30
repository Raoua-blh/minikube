import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { EmployeeService } from '../employee.service';
import { Employee } from '../models/employee';
import { Router } from '@angular/router';
import { EmployeeUpdateComponent } from '../employee-update/employee-update.component';
import { EmployeeAddComponent } from '../employee-add/employee-add.component';
@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  displayedColumns: string[] = ['id', 'firstname', 'lastname', 'actions'];

  constructor(private employeeService: EmployeeService, private router: Router,public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.employeeService.getEmployees().subscribe((data: Employee[]) => {
      this.employees = data;
    });
  }

  createEmployee():void{
    const dialogRef = this.dialog.open(EmployeeAddComponent, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadEmployees(); // Refresh the list after adding
      }
    });
  }
  
  updateEmployee(id: number): void {
    const employee = this.employees.find(e => e.id === id);
    const dialogRef = this.dialog.open(EmployeeUpdateComponent, {
      width: '420px',
      data: { employee }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadEmployees(); // Refresh the list after update
    });
  }

  deleteEmployee(id: number): void {
    this.employeeService.deleteEmployee(id).subscribe(() => {
      this.loadEmployees(); // Refresh the list after deletion
    });
  }
}