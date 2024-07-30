package com.Fenleap.Demo.Controller;

import com.Fenleap.Demo.Model.Employe;
import com.Fenleap.Demo.Services.EmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/v1")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://192.168.1.21:30000")

public class EmployeCtrl {
    private final EmployeService employeeService;

    /**
     * This method is called when a GET request is made
     * URL: localhost:8089/employee/v1/
     * Purpose: Fetches all the employees in the employee table
     * @return List of Employees
     */
    @GetMapping("/")
    public ResponseEntity<List<Employe>> getAllEmployees(){
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    /**
     * This method is called when a GET request is made
     * URL: localhost:8089/employee/v1/1 (or any other id)
     * Purpose: Fetches employee with the given id
     * @param id - employee id
     * @return Employee with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeeById(@PathVariable Integer id)
    {
        return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
    }

    /**
     * This method is called when a POST request is made
     * URL: localhost:8089/employee/v1/
     * Purpose: Save an Employee entity
     * @param employee - Request body is an Employee entity
     * @return Saved Employee entity
     */
    @PostMapping("/")
    public ResponseEntity<Employe> saveEmployee(@RequestBody Employe employee)
    {
        return ResponseEntity.ok().body(employeeService.saveEmployee(employee));
    }

    /**
     * This method is called when a PUT request is made
     * URL: localhost:8089/employee/v1/
     * Purpose: Update an Employee entity
     * @param employee - Employee entity to be updated
     * @return Updated Employee
     */
    @PutMapping("/")
    public ResponseEntity<Employe> updateEmployee(@RequestBody Employe employee)
    {
        return ResponseEntity.ok().body(employeeService.updateEmployee(employee));
    }

    /**
     * This method is called when a PUT request is made
     * URL: localhost:8089/employee/v1/1 (or any other id)
     * Purpose: Delete an Employee entity
     * @param id - employee's id to be deleted
     * @return a String message indicating employee record has been deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer id)
    {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok().body("Deleted employee successfully");
    }


}
