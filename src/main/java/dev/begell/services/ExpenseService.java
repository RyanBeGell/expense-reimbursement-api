package dev.begell.services;

import dev.begell.entities.Expense;

import java.util.List;

public interface ExpenseService {

    Expense registerNewExpense(Expense expense);

    Expense retrieveExpenseByExpenseId(int id);

    Expense approveExpense(Expense expense);

    Expense denyExpense(Expense expense);

    Expense deleteExpenseById(int id);

    Expense replaceExpense(Expense expense);

    List<Expense> getAllExpensesByEmployeeId(int id);

    List<Expense> getAllExpenses();

    List<Expense> getAllPendingExpenses();
}
