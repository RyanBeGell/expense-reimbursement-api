package servicetests;

import dev.begell.data.EmployeeDAO;
import dev.begell.data.EmployeeDAOPostgresImpl;
import dev.begell.data.ExpenseDAO;
import dev.begell.data.ExpenseDAOPostgresImpl;
import dev.begell.entities.Employee;
import dev.begell.entities.Expense;
import dev.begell.exceptions.ImmutableExpenseException;
import dev.begell.exceptions.ResourceNotFoundException;
import dev.begell.services.ExpenseService;
import dev.begell.services.ExpenseServiceImpl;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //for running ordered test cases
public class ExpenseServiceTests {

    public static ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDAOPostgresImpl());
    static ExpenseDAO expenseDAO = new ExpenseDAOPostgresImpl();
    static Expense testExpense = null;
    static Expense testExpense2 = null;
    static Expense testExpense3 = null;
    static Expense testExpense4 = null;
    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();

    //a few employees to use to satisfy foreign key on expense tests
    static Employee employee = new Employee(0,"Tommy", "Tester");
    static Employee employee2 = new Employee(0,"Timmy", "Testerman");
    static Employee testEmployee = employeeDAO.createEmployee(employee);
    static Employee testEmployee2 = employeeDAO.createEmployee(employee2);
    
    
    @Test
    @Order(1)
    void get_all_expenses(){
        Expense a = new Expense(0,100.0, testEmployee.getEmployeeId(),"test");
        Expense b = new Expense(0,200.0, testEmployee.getEmployeeId(), "test");
        Expense c = new Expense(0,300.0, testEmployee.getEmployeeId(), "test");
        ExpenseServiceTests.testExpense2 = expenseService.registerNewExpense(a);   //saving one of the expenses for another test
        ExpenseServiceTests.testExpense3 = expenseService.registerNewExpense(b);
        ExpenseServiceTests.testExpense4 = expenseService.registerNewExpense(c);
        List<Expense> expenses = expenseService.getAllExpenses();
        int totalExpenses = expenses.size();
        Assertions.assertTrue(totalExpenses >= 3);
    }

    @Test
    @Order(2)
    void register_expense(){
        Expense food = new Expense(0,10.0, testEmployee.getEmployeeId(),"Subway Footlong");
        Expense savedExpense = expenseService.registerNewExpense(food);
        ExpenseServiceTests.testExpense = savedExpense;
        Assertions.assertNotEquals(0,savedExpense.getExpenseId());
    }

    @Test
    @Order(3)
    void get_expense_by_id(){
        //get saved expense id
        Expense retrievedExpense  = expenseService.retrieveExpenseByExpenseId(testExpense.getExpenseId());
        Assertions.assertEquals("Subway Footlong", retrievedExpense.getExpenseDescription());
        Assertions.assertEquals(testEmployee.getEmployeeId(), retrievedExpense.getEmpId());
        Assertions.assertEquals(10, retrievedExpense.getAmount());
    }

    @Test
    @Order(4)
    void replace_expense(){
        ExpenseServiceTests.testExpense.setExpenseDescription("Replacement test");
        expenseService.replaceExpense(testExpense);// the new title should be saved to the database
        Expense retrievedExpense = expenseService.retrieveExpenseByExpenseId(testExpense.getExpenseId());
        Assertions.assertEquals("Replacement test",retrievedExpense.getExpenseDescription());
    }

    @Test
    @Order(5)
    void delete_expense_by_id(){
        boolean result = expenseService.deleteExpenseById(testExpense.getExpenseId()); // true if successful
        Assertions.assertTrue(result);
        //assert that the expense is not found when searched for
        Assertions.assertThrows(ResourceNotFoundException.class,() ->
                expenseDAO.getExpenseById(testExpense.getExpenseId()));
    }

    @Test
    @Order(6)
    void get_non_existent_expense() {
        Assertions.assertThrows(ResourceNotFoundException.class,() ->
                expenseService.retrieveExpenseByExpenseId(100000));
    }

    @Test
    @Order(7)
    void approve_pending_expense(){
        Assertions.assertTrue(expenseService.approveExpense(testExpense4));
    }

    @Test
    @Order(8)
    void deny_pending_expense(){
        Assertions.assertTrue(expenseService.approveExpense(testExpense2));
    }

    @Test
    @Order(9)
    void approve_denied_expense(){
        Assertions.assertThrows(ImmutableExpenseException.class,() ->
                expenseService.approveExpense(testExpense2));
    }

    @Test
    @Order(10)
    void delete_approved_expense(){
        Assertions.assertThrows(ImmutableExpenseException.class,() ->
                expenseService.approveExpense(testExpense4));
    }
    @Test
    @Order(11)
    void delete_denied_expense(){
        Assertions.assertThrows(ImmutableExpenseException.class,() ->
                expenseService.approveExpense(testExpense2));
    }

    @Test
    @Order(12)
    void delete_pending_expense(){
        Assertions.assertTrue(expenseService.deleteExpenseById(testExpense3.getExpenseId()));
    }

    @Test
    @Order(13)
    void get_all_pending_expenses(){
        Expense a = new Expense(0,100.0, testEmployee.getEmployeeId(), "test");
        Expense b = new Expense(0,200.0, testEmployee.getEmployeeId(), "test");
        Expense c = new Expense(0,300.0, testEmployee.getEmployeeId(), "test");
        expenseService.registerNewExpense(a);
        expenseService.registerNewExpense(b);
        expenseService.registerNewExpense(c);
        List<Expense> expenses = expenseService.getAllPendingExpenses();
        int totalExpenses = expenses.size();
        Assertions.assertTrue(totalExpenses >= 3);  //we've just added 3 pending expenses, should be min 3
    }

    @Test
    @Order(14)
    void get_all_expenses_by_employee_id(){
        List<Expense> expenses = expenseService.getAllExpensesByEmployeeId(testEmployee.getEmployeeId());
        int totalExpenses = expenses.size();
        Assertions.assertTrue(totalExpenses >= 2);  //in test above three expenses were added
    }

}

