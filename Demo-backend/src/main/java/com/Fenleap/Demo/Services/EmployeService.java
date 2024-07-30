package com.Fenleap.Demo.Services;

import com.Fenleap.Demo.Model.Employe;
import com.Fenleap.Demo.Repo.EmployeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeService {
    @Autowired
    private final EmployeRepo employeeRepo;

    public List<Employe> getAllEmployees(){
        return employeeRepo.findAll();
    }

    public Employe getEmployeeById(Integer id){
        Optional<Employe> optionalEmployee = employeeRepo.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        log.info("Employee with id: {} doesn't exist", id);
        return null;
    }

    public Employe saveEmployee (Employe employee){
        Employe savedEmployee = employeeRepo.save(employee);

        log.info("Employee with id: {} saved successfully", employee.getId());
        return savedEmployee;
    }

    public Employe updateEmployee (Employe employee) {
        Optional<Employe> existingEmployee = employeeRepo.findById(employee.getId());
        Employe updatedEmployee = employeeRepo.save(employee);
        log.info("Employee with id: {} updated successfully", employee.getId());
        return updatedEmployee;
    }

    public void deleteEmployeeById (Integer id) {
        employeeRepo.deleteById(id);
    }


}
