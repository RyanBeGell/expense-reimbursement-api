package dev.begell.data;

import dev.begell.entities.Employee;

//DAO for CRUD operations on the database for the Employee entity

public interface EmployeeDAO {

    //create
    Employee createEmployee(Employee employee);

    //read
    Employee getEmployeeById(int id);

    //update
    Employee updateEmployee(Employee employee);

    //delete
    boolean deleteEmployeeById(int id);

}
