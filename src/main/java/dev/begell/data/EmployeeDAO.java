package dev.begell.data;

import dev.begell.entities.Employee;

import java.util.List;

//DAO for CRUD operations on the database for the Employee entity

public interface EmployeeDAO {

    //create
    Employee createEmployee(Employee employee);

    //read
    Employee getEmployeeById(int id);

    List<Employee> getAllEmployees();

    //update
    Employee updateEmployee(Employee employee);

    //delete
    boolean deleteEmployeeById(int id);

}
