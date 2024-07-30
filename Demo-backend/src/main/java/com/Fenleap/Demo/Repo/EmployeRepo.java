package com.Fenleap.Demo.Repo;

import com.Fenleap.Demo.Model.Employe;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeRepo extends JpaRepository<Employe, Integer>
{
}
