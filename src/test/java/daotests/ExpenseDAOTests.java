package daotests;

import dev.begell.data.EmployeeDAO;
import dev.begell.data.EmployeeDAOPostgresImpl;
import dev.begell.data.ExpenseDAO;
import dev.begell.data.ExpenseDAOPostgresImpl;
import dev.begell.entities.Employee;
import dev.begell.entities.Expense;
import dev.begell.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.*;

import java.util.Date;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //for running ordered test cases
public class ExpenseDAOTests {

    static Date date = new Date();
    static ExpenseDAO expenseDAO = new ExpenseDAOPostgresImpl();
    static Expense testExpense = null;
    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();

    //Create employees to satisfy foreign key constraint and give a real emp_id to my expense tests
    static Employee employee = new Employee(0,"Terry", "Tester");
    static Employee employee2 = new Employee(0,"Terry2", "Tester2");
    static Employee testEmployee = employeeDAO.createEmployee(employee);
    static Employee testEmployee2 = employeeDAO.createEmployee(employee2);

    @Test
    @Order(1) // JUnit does not run tests in order by default
    void create_expense_test(){
        // An entity that is created but not yet saved should have an id of 0
        // once saved that Expense should be some non-zero value

        Expense expense1 = new Expense(0,500, testEmployee.getEmployeeId(), date.getTime(),
                "Food");
        Expense savedExpense = expenseDAO.createExpense(expense1);
        ExpenseDAOTests.testExpense = savedExpense;// An Expense I can use in other tests
        Assertions.assertNotEquals(0, savedExpense.getAmount());
    }

    @Test
    @Order(2)
    void get_Expense_by_id(){
        // use the id generated from the previous test
        Expense retrievedExpense  = expenseDAO.getExpenseById(testExpense.getExpenseId());
        // I have avoided hard coded values and made my test repeatable
        Assertions.assertEquals(testExpense.getEmpId(), retrievedExpense.getEmpId());
        Assertions.assertEquals(testExpense.getAmount(), retrievedExpense.getAmount());
    }

    @Test
    @Order(3)
    void update_Expense(){
        ExpenseDAOTests.testExpense.setApproval("denied");
        ExpenseDAOTests.testExpense.setAmount(1000);
        ExpenseDAOTests.testExpense.setEmpId(testEmployee2.getEmployeeId());
        ExpenseDAOTests.testExpense.setExpenseDescription("Travel");
        expenseDAO.updateExpense(testExpense);// the new title should be saved to the database
        Expense retrievedExpense = expenseDAO.getExpenseById(testExpense.getExpenseId());
        Assertions.assertEquals(testEmployee2.getEmployeeId(),retrievedExpense.getEmpId());
        Assertions.assertEquals("Travel", retrievedExpense.getExpenseDescription());
    }

//    @Test
//    @Order(4)// to not run something
//    void delete_Expense(){
//        boolean result = expenseDAO.deleteExpenseById(testExpense.getExpenseId()); // true if successful
//        Assertions.assertTrue(result);
//    }

    @Test
    @Order(5)
    void get_non_existent_expense() {
        Assertions.assertThrows(ResourceNotFoundException.class,() ->
                expenseDAO.getExpenseById(10000));
    }

    @Test
    @Order(6)
    void get_all_expenses(){
        Expense a = new Expense(0,100.0, testEmployee.getEmployeeId(), date.getTime(), "test");
        Expense b = new Expense(0,200.0, testEmployee.getEmployeeId(), date.getTime(), "test");
        Expense c = new Expense(0,300.0, testEmployee.getEmployeeId(), date.getTime(), "test");
        expenseDAO.createExpense(a);
        expenseDAO.createExpense(b);
        expenseDAO.createExpense(c);
        List<Expense> expenses = expenseDAO.getAllExpenses();
        int totalExpenses = expenses.size();
        Assertions.assertTrue(totalExpenses >= 3);
    }

}
