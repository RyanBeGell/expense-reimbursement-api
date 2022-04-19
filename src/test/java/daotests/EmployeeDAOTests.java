package daotests;

import dev.begell.data.EmployeeDAO;
import dev.begell.data.EmployeeDAOPostgresImpl;
import dev.begell.entities.Employee;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //for running ordered test cases
public class EmployeeDAOTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();
    static Employee testEmployee = null;

    @Test
    @Order(1) // JUnit does not run tests in order by default
    void create_employee_test(){
        // An entity that is created but not yet saved should have an id of 0
        // once saved that Employee should be some non-zero value
        Employee frank = new Employee(0,"Franklin","Redhot");
        Employee savedEmployee = employeeDAO.createEmployee(frank);
        EmployeeDAOTests.testEmployee = savedEmployee;// I have a Employee I can use in other tests
        Assertions.assertNotEquals(0,savedEmployee.getEmployeeId());
    }

    @Test
    @Order(2)
    void get_Employee_by_id(){
        Employee retrievedEmployee  = employeeDAO.getEmployeeById(testEmployee.getEmployeeId());// use the id generated from the previous test
        // I have avoided hard coded values and made my test repeatable
        Assertions.assertEquals("Franklin", retrievedEmployee.getFirstName());
        Assertions.assertEquals("Redhot", retrievedEmployee.getLastName());
    }

    @Test
    @Order(3)
    void update_Employee(){
        EmployeeDAOTests.testEmployee.setFirstName("Tommy");
        EmployeeDAOTests.testEmployee.setLastName("Tabasco");
        employeeDAO.updateEmployee(testEmployee);// the new title should be saved to the database
        Employee retrievedEmployee = employeeDAO.getEmployeeById(testEmployee.getEmployeeId());
        Assertions.assertEquals("Tommy",retrievedEmployee.getFirstName());
        Assertions.assertEquals("Tabasco", retrievedEmployee.getLastName());
    }

    @Test
    @Order(4)// to not run something
    void delete_Employee(){
        boolean result = employeeDAO.deleteEmployeeById(testEmployee.getEmployeeId()); // true if successful
        Assertions.assertTrue(result);
    }

    @Test
    @Order(5)
    void get_non_existent_Employee() {
        System.out.println("This exception stack trace is an intentional");
        System.out.println("-----------------------------------------------------------------------------------------");
        Assertions.assertNull(employeeDAO.getEmployeeById(10000));
    }
}
