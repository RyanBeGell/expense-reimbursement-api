package dev.begell.services;

import dev.begell.entities.Employee;

import java.util.List;

public interface EmployeeService {
    Employee registerNewEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int ID);

    Employee replaceEmployee(Employee employee);

}

