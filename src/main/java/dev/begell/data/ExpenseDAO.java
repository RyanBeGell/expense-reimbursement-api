package dev.begell.data;


import dev.begell.entities.Expense;

//DAO for CRUD operations on the database for the Expense entity
public interface ExpenseDAO {

    //create
    Expense createExpense(Expense expense);

    //read
    Expense getExpenseById(int id);

    //update
    Expense updateExpense(Expense expense);

    //delete
    boolean deleteExpenseById(int id);


}
