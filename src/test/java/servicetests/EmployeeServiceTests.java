package servicetests;

import dev.begell.data.EmployeeDAOPostgresImpl;
import dev.begell.entities.Employee;
import dev.begell.services.EmployeeService;
import dev.begell.services.EmployeeServiceImpl;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //for running ordered test cases
public class EmployeeServiceTests {

    public static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOPostgresImpl());
    public static Employee testEmployee = null;

    @Test
    @Order(1)
    void get_all_employees(){
        Employee a = new Employee(0,"A","A");
        Employee b = new Employee(0,"B","B");
        Employee c = new Employee(0,"C","C");
        employeeService.registerNewEmployee(a);
        employeeService.registerNewEmployee(b);
        employeeService.registerNewEmployee(c);
        List<Employee> employees = employeeService.getAllEmployees();
        int totalEmployees = employees.size();
        Assertions.assertTrue(totalEmployees >= 3);
    }

    @Test
    @Order(2)
    void register_employee(){
        Employee fred = new Employee(0,"Freddie","Testerman");
        Employee savedEmployee = employeeService.registerNewEmployee(fred);
        EmployeeServiceTests.testEmployee = savedEmployee;
        Assertions.assertNotEquals(0,savedEmployee.getEmployeeId());
    }

    @Test
    @Order(3)
    void get_employee_by_id(){
        //get saved employee id
        Employee retrievedEmployee  = employeeService.getEmployeeById(testEmployee.getEmployeeId());
        Assertions.assertEquals("Freddie", retrievedEmployee.getFirstName());
        Assertions.assertEquals("Testerman", retrievedEmployee.getLastName());
    }

    @Test
    @Order(4)
    void replace_employee(){
        EmployeeServiceTests.testEmployee.setFirstName("FreddieReplacement");
        EmployeeServiceTests.testEmployee.setLastName("TestermanReplacement");
        employeeService.replaceEmployee(testEmployee);// the new title should be saved to the database
        Employee retrievedEmployee = employeeService.getEmployeeById(testEmployee.getEmployeeId());
        Assertions.assertEquals("FreddieReplacement",retrievedEmployee.getFirstName());
    }

    @Test
    @Order(5)
    void delete_employee_by_id(){
        boolean result = employeeService.deleteEmployeeById(testEmployee.getEmployeeId()); // true if successful
        Assertions.assertTrue(result);
    }



}