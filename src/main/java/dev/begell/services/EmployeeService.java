package dev.begell.services;

import dev.begell.entities.Employee;

public interface EmployeeService {

    Employee registerNewEmployee(Employee employee);

    Employee getApproverByExpenseId(int id);    //find out who approved an expense

}
