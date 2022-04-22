package dev.begell.data;

import dev.begell.entities.Expense;
import dev.begell.exceptions.ResourceNotFoundException;
import dev.begell.utilities.ConnectionUtil;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
public class ExpenseDAOPostgresImpl implements ExpenseDAO{

    java.util.Date date = new Date();

    public Expense createExpense(Expense expense) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into expense values(default,?,?,?,?,?)";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, expense.getAmount());
            ps.setInt(2, expense.getEmpId());
            ps.setBoolean(3,false);
            ps.setLong(4, date.getTime());
            ps.setString(5, expense.getExpenseDescription());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt("expense_id");
            expense.setExpenseId(generatedId);
            log.info("Expense #" + expense.getExpenseId() + "\tsuccessfully created.");
            return expense;
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Expense getExpenseById(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from expense where expense_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Expense expense = new Expense();
            if (rs.next()) {    //if there is a first record
                expense.setExpenseId((rs.getInt("expense_id")));
                expense.setAmount(rs.getDouble("amount"));
                expense.setEmpId(rs.getInt("emp_id"));
                expense.setExpenseDate(rs.getLong("expense_date"));
                expense.setExpenseDescription(rs.getString("expense_description"));
            }
            else
                throw new ResourceNotFoundException(id);
            return expense;

        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Expense> getAllExpenses() {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from expense";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Expense> Expenses = new ArrayList<>();
            while (rs.next()){
                Expense expense = new Expense();
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setEmpId(rs.getInt("emp_id"));
                expense.setExpenseDate(rs.getLong("expense_date"));
                expense.setExpenseDescription(rs.getString("expense_description"));
                Expenses.add(expense);
            }

            return Expenses;

        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Expense updateExpense(Expense expense) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update expense set amount = ?, emp_id = ?, is_approved = ?, expense_date = ?, " +
                    "expense_description = ?, where expense_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, expense.getAmount());
            ps.setInt(2, expense.getEmpId());
            ps.setBoolean(3, expense.isApproved());
            ps.setLong(4, date.getTime());
            ps.setString(5, expense.getExpenseDescription());
            ps.setInt(6, expense.getEmpId());
            int rowsUpdated = ps.executeUpdate();

            if(rowsUpdated == 0){
                throw new ResourceNotFoundException(expense.getExpenseId());
            }
            else {
                log.info("Expense #" + expense.getExpenseId() + "\tsuccessfully updated.");
                return expense;
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean deleteExpenseById(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from expense where expense_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            log.info("Expense #" + id + "\tsuccessfully deleted.");
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
