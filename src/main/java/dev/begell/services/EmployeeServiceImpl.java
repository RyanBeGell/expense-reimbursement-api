package dev.begell.services;

import dev.begell.data.EmployeeDAO;
import dev.begell.entities.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    final private EmployeeDAO employeeDAO;

    //dependency injection
    public EmployeeServiceImpl(EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
    }


    @Override
    public Employee registerNewEmployee(Employee employee) {
        return this.employeeDAO.createEmployee(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeDAO.getAllEmployees();
    }
}
