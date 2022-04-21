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

    @Override
    public Employee getEmployeeById(int ID) {
        return employeeDAO.getEmployeeById(ID);
    }

    @Override
    public Employee replaceEmployee(Employee employee) {
        return this.employeeDAO.updateEmployee(employee);
    }

    @Override
    public boolean deleteEmployeeById(int id) {
        if(this.employeeDAO.getEmployeeById(id)!= null) {
            return this.employeeDAO.deleteEmployeeById(id);
        } else {
            return false;
        }
    }
}
