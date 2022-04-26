package dev.begell.services;

import dev.begell.data.ExpenseDAO;
import dev.begell.entities.Expense;
import dev.begell.exceptions.ImmutableExpenseException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
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
        if(expense.getApproval().equals("approved") || expense.getApproval().equals("denied")) {
            log.warn("An attempt was made to change expense #[" + expense.getExpenseId() + "]");
            throw new ImmutableExpenseException(expense.getExpenseId());
        } else {
            expense.setApproval("approved");
            expenseDAO.updateExpense(expense);
            return true;
        }
    }

    @Override
    public boolean denyExpense(Expense expense) {
        if (expense.getApproval().equals("approved") || expense.getApproval().equals("denied")){
            log.warn("An attempt was made to change expense #[" + expense.getExpenseId() + "]");
            throw new ImmutableExpenseException(expense.getExpenseId());
        } else {
            expense.setApproval("denied");
            expenseDAO.updateExpense(expense);
            return true;
        }
    }

    @Override
    public Boolean deleteExpenseById(int id) {
        if (expenseDAO.getExpenseById(id).getApproval().equals("approved") ||
                expenseDAO.getExpenseById(id).getApproval().equals("denied")){
            log.warn("An attempt was made to delete approved expense #[" + id + "]");
            throw new ImmutableExpenseException(id);
        } else {
            return this.expenseDAO.deleteExpenseById(id);
        }
    }

    @Override
    public Expense replaceExpense(Expense expense) {
        if (expense.getApproval().equals("approved") || expense.getApproval().equals("denied")){
            log.warn("An attempt was made to change expense #[" + expense.getExpenseId() + "]");
            throw new ImmutableExpenseException(expense.getExpenseId());
        } else {
            return this.expenseDAO.updateExpense(expense);
        }
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
            if(!expense.getApproval().equals("pending"))
                expenses.remove(expense);
        });
        return expenses;
    }
}
