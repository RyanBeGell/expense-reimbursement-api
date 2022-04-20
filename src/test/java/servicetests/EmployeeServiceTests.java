package servicetests;

import dev.begell.data.EmployeeDAO;
import dev.begell.data.EmployeeDAOPostgresImpl;
import dev.begell.entities.Employee;
import dev.begell.services.EmployeeService;
import dev.begell.services.EmployeeServiceImpl;
import org.junit.jupiter.api.*;


public class EmployeeServiceTests {

    public static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOPostgresImpl());
    public static Employee testEmployee = null;

    @Test
    void register_employee_test(){
        Employee fred = new Employee(0,"Fred","Draper");
        Employee savedEmployee = employeeService.registerNewEmployee(fred);
        Assertions.assertNotEquals(0,savedEmployee.getEmployeeId());
    }

}