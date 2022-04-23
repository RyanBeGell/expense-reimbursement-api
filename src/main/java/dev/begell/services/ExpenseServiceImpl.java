package dev.begell.services;

import dev.begell.data.ExpenseDAO;
import dev.begell.entities.Expense;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseDAO expenseDAO;

    //dependency injection
    public ExpenseServiceImpl(ExpenseDAO expenseDAO){
        this.expenseDAO = expenseDAO;
    }

    @Override
    public Expense registerNewExpense(Expense expense) {
        return this.expenseDAO.createExpense(expense);
    }

    @Override
    public Expense retrieveExpenseByExpenseId(int id) {
        return expenseDAO.getExpenseById(id);
    }

    @Override
    public boolean approveExpense(Expense expense) {
        expense.setApproved(true);
        return true;
    }

    @Override
    public boolean denyExpense(Expense expense) {
        expense.setApproved(false);
        return true;
    }

    @Override
    public Boolean deleteExpenseById(int id) {
        if(this.expenseDAO.getExpenseById(id) != null) {
            return this.expenseDAO.deleteExpenseById(id);
        } else {
            return false;
        }
    }

    @Override
    public Expense replaceExpense(Expense expense) {
        return this.expenseDAO.updateExpense(expense);
    }

    @Override
    public List<Expense> getAllExpensesByEmployeeId(int id) {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        expenses.forEach(expense ->{
            if(expense.getExpenseId() != id){
                expenses.remove(expense);
            }
        });
        return expenses;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }

    @Override
    public List<Expense> getAllPendingExpenses() {
        List<Expense> expenses = expenseDAO.getAllExpenses();
        expenses.forEach(expense -> {
            if(!expense.isApproved())
                expenses.remove(expense);
        });
        return expenses;
    }
}
