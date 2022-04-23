package dev.begell.api;

import com.google.gson.Gson;
import dev.begell.exceptions.ResourceNotFoundException;
import io.javalin.Javalin;
import dev.begell.entities.*;
import dev.begell.data.*;
import dev.begell.services.*;
import java.util.List;

public class App {

    static EmployeeService employeeService = new EmployeeServiceImpl(new EmployeeDAOPostgresImpl());
    static ExpenseService expenseService = new ExpenseServiceImpl(new ExpenseDAOPostgresImpl());
    static Gson gson = new Gson();

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        //create
        app.post("/employees", context -> {
            String body = context.body();
            Employee employee = gson.fromJson(body, Employee.class);// JSON fields need to match what the fields are in the class
            employeeService.registerNewEmployee(employee);
            context.status(201);// successfully created employee - 201 for creation
            String EmployeeJSON = gson.toJson(employee);
            context.result("Employee added successfully: " + EmployeeJSON);
        });

        //read
        //get all employees
        app.get("/employees", context -> {
            List<Employee> employeeList = employeeService.getAllEmployees();
            String json = gson.toJson((employeeList));
            context.result(json);
        });

        //get specific employee
        app.get("/employees/{id}", context -> {

            int id = Integer.parseInt(context.pathParam("id"));

            try {
                String employeeJSON =  gson.toJson(employeeService.getEmployeeById(id));
                context.result(employeeJSON);
            }catch (ResourceNotFoundException e){
                context.status(404);
                context.result("Employee #[" + id + "] was not found");
            }

        });

        //UPDATE
        app.put("/employees/{id}",context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                String body = context.body();
                Employee employee = gson.fromJson(body, Employee.class);
                employee.setEmployeeId(id);// the id in the uri should take precedence
                Employee newEmployee = employeeService.replaceEmployee(employee);
                context.result("Employee #[ " + id + " ] " + "replaced with " +
                        newEmployee.getFirstName() + " " + newEmployee.getLastName());
            }catch (ResourceNotFoundException e){
                context.status(404);
                context.result("Employee #[" + id + "] was not found.");
            }
        });

        //DELETE
        app.delete("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                boolean result = employeeService.deleteEmployeeById(id);
                if (result) {
                    context.result("Employee #[" + id + "] successfully deleted.");
                }}catch(ResourceNotFoundException e){
                    context.status(404);
                    context.result("Employee #[" + id + "] was not found.");
                }
        });


        //expense paths
        //create
        app.post("/expenses", context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);// JSON fields need to match what the fields are in the class
            expenseService.registerNewExpense(expense);
            context.status(201);// successfully created expense - 201 for creation
            String ExpenseJSON = gson.toJson(expense);
            context.result("Expense added successfully: " + ExpenseJSON);
        });

        //adds expense to specific employee
        app.post("/employee/{id}/expenses", context -> {
            String body = context.body();
            int id = Integer.parseInt(context.pathParam("id"));
            Expense expense = gson.fromJson(body, Expense.class);// JSON fields need to match what the fields are in the class
            expense.setEmpId(id);
            expenseService.registerNewExpense(expense);
            context.status(201);// successfully created expense - 201 for creation
            String ExpenseJSON = gson.toJson(expense);
            context.result("Expense added successfully to employee #[" + id + "] : " + ExpenseJSON);
        });

        app.post("/expenses", context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);// JSON fields need to match what the fields are in the class
            expenseService.registerNewExpense(expense);
            context.status(201);// successfully created expense - 201 for creation
            String ExpenseJSON = gson.toJson(expense);
            context.result("Expense added successfully: " + ExpenseJSON);
        });


        //read
        //get all expenses
        app.get("/expenses", context -> {
            List<Expense> expenseList = expenseService.getAllExpenses();
            String json = gson.toJson((expenseList));
            context.result(json);
        });

        //get all pending expenses
        app.get("/expenses", context -> {
            List<Expense> expenseList = expenseService.getAllPendingExpenses();
            String json = gson.toJson((expenseList));
            context.result(json);
        });

        //get specific expense
        app.get("/employees/{id}/expenses", context -> {

            int id = Integer.parseInt(context.pathParam("id"));

            try {
                String expenseJSON =  gson.toJson(expenseService.retrieveExpenseByExpenseId(id));
                context.result(expenseJSON);
            }catch (ResourceNotFoundException e){
                context.status(404);
                context.result("Employee #[" + id + "] was not found");
            }
        });

        //get all expenses from a specific employee
        app.get("/employees/{id}/expenses", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                List<Expense> expenseList = expenseService.getAllExpensesByEmployeeId(id);
                String json = gson.toJson((expenseList));
                context.result(json);
            }catch (ResourceNotFoundException e){
                context.status(404);
                context.result("Employee #[" + id + "] was not found");
            }
        });

        //UPDATE

        //replace expense
        app.put("/expenses/{id}",context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                String body = context.body();
                Expense expense = gson.fromJson(body, Expense.class);
                expense.setExpenseId(id);// the id in the uri should take precedence
                expenseService.replaceExpense(expense);
                context.result("Expense #[ " + id + " ] " + "replaced.");
            }catch (ResourceNotFoundException e){
                context.status(404);
                context.result("Expense #[" + id + "] was not found.");
            }
        });

        //approve expense
        app.patch("/expenses/{id}/approve", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try{
                String body = context.body();
                Expense expense = gson.fromJson(body, Expense.class);
                expense.setApproved(true); //approve the expense
                context.result("Expense #[ " + id + " ] " + "approved.");
            }catch (ResourceNotFoundException e){
                context.status(404);
                context.result("Expense #[" + id + "] was not found.");
            }
        });

        //deny expense
        app.patch("/expenses/{id}/deny", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try{
                String body = context.body();
                Expense expense = gson.fromJson(body, Expense.class);
                expense.setApproved(false); //approve the expense
                context.result("Expense #[ " + id + " ] " + "denied.");
            }catch (ResourceNotFoundException e){
                context.status(404);
                context.result("Expense #[" + id + "] was not found.");
            }
        });

        //DELETE
        app.delete("/expenses/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                boolean result = expenseService.deleteExpenseById(id);
                if (result) {
                    context.result("Expense #[" + id + "] successfully deleted.");
                }}catch(ResourceNotFoundException e){
                context.status(404);
                context.result("Expense #[" + id + "] was not found.");
            }
        });



        //start app on port 7000
        app.start(7000);
    }
}
