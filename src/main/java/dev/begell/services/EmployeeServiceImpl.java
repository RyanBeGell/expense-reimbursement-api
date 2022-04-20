package dev.begell.services;

import dev.begell.data.EmployeeDAO;
import dev.begell.entities.Employee;

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
}
