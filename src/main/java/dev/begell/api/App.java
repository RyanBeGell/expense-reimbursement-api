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
    static Gson gson = new Gson();

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        //create
        app.post("/employees", context -> {
            String body = context.body();
            Employee Employee = gson.fromJson(body, Employee.class);// JSON fields need to match what the fields are in the class
            employeeService.registerNewEmployee(Employee);
            context.status(201);// successfully created employee - 201 for creation
            String EmployeeJSON = gson.toJson(Employee);
            context.result(EmployeeJSON);
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
                context.result("The employee id " + id + "was not found");
            }

        });

        //UPDATE
        app.put("/employees/{id}",context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                String body = context.body();
                Employee employee = gson.fromJson(body, Employee.class);
                employee.setEmployeeId(id);// the id in the uri should take precedence
                Employee newEmployee;
                newEmployee = employeeService.replaceEmployee(employee);
                context.result("Employee #[ " + id + " ] " + "replaced with " +
                        newEmployee.getFirstName() + " " + newEmployee.getLastName());
            }catch (ResourceNotFoundException e){
                context.status(404);
                context.result("The employee id " + id + "was not found");
            }
        });

        //DELETE
        app.delete("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            boolean result = employeeService.deleteEmployeeById(id);
            if(result){
                context.status(204);
                context.result("Employee # [ " + id + " ] successfully deleted");
            }else{
                context.status(404);
                context.result("The employee id " + id + "was not found");
            }
        });

        //start app on port 7000
        app.start(7000);
    }
}
