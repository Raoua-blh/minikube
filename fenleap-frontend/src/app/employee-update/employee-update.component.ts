import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EmployeeService } from '../employee.service';
import { Employee } from '../models/employee' ;
@Component({
  selector: 'app-employee-update',
  templateUrl: './employee-update.component.html',
  styleUrls: ['./employee-update.component.css']
})
export class EmployeeUpdateComponent implements OnInit {
  employee: Employee;

  constructor(
    public dialogRef: MatDialogRef<EmployeeUpdateComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private employeeService: EmployeeService
  ) {
    this.employee = data.employee;
  }

  ngOnInit(): void {}

  onUpdate(): void {
    this.employeeService.updateEmployee(this.employee.id, this.employee).subscribe(() => {
      this.dialogRef.close();
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
