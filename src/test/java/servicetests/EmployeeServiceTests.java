package servicetests;

import dev.begell.data.EmployeeDAO;
import dev.begell.data.EmployeeDAOPostgresImpl;
import dev.begell.entities.Employee;
import dev.begell.services.EmployeeService;
import dev.begell.services.EmployeeServiceImpl;
import org.junit.jupiter.api.*;

import java.util.List;


public class EmployeeServiceTests {

    public static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOPostgresImpl());
    public static Employee testEmployee = null;

    @Test
    void register_employee_test(){
        Employee fred = new Employee(0,"Fred","Draper");
        Employee savedEmployee = employeeService.registerNewEmployee(fred);
        Assertions.assertNotEquals(0,savedEmployee.getEmployeeId());
    }

    @Test
    void get_all_employees(){
        Employee a = new Employee(0,"A","A");
        Employee b = new Employee(0,"B","B");
        Employee c = new Employee(0,"C","C");
        List<Employee> employees = employeeService.getAllEmployees();
        int totalEmployees = employees.size();
        Assertions.assertTrue(totalEmployees >= 3);
    }

}