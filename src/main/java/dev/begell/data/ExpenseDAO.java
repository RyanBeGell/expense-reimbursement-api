package dev.begell.data;


import dev.begell.entities.Employee;
import dev.begell.entities.Expense;

import java.util.List;

//DAO for CRUD operations on the database for the Expense entity
public interface ExpenseDAO {

    //create
    Expense createExpense(Expense expense);

    //read
    Expense getExpenseById(int id);

    List<Expense> getAllExpenses();

    //update
    Expense updateExpense(Expense expense);

    //delete
    boolean deleteExpenseById(int id);


}
