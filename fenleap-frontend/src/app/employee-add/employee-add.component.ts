import { Component, OnInit } from '@angular/core';
import { Employee } from '../models/employee';
import { EmployeeService } from '../employee.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.css']
})
export class EmployeeAddComponent  {
  employee: Employee = new Employee();

  constructor(
    public dialogRef: MatDialogRef<EmployeeAddComponent>,
    private employeeService: EmployeeService
  ) {}

  onSave(): void {
    this.employeeService.createEmployee(this.employee).subscribe(() => {
      this.dialogRef.close(true);
    }, error => {
      console.error('Error adding employee', error);
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
