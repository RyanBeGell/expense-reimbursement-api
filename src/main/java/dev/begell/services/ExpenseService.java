package dev.begell.services;

import dev.begell.entities.Expense;

import java.util.List;

public interface ExpenseService {

    Expense registerNewExpense(Expense expense);

    Expense retrieveExpenseById(Expense expense);

    Expense approveExpense(Expense expense);

    Expense deleteExpenseById(int id);

    List<Expense> getAllExpensesById(int id);


}
