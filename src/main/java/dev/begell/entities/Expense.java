package dev.begell.entities;

import lombok.*;


@Data
@NoArgsConstructor
public class Expense {
    private int expenseId;
    @Setter(AccessLevel.NONE)   //stop the lombok @data from auto-generating the amount setter
    private double amount;
    private int empId;
    private String approval = "pending";
    private long expenseDate;
    private String expenseDescription;

    //some args constructor without approval status
    public Expense(int expenseId, double amount, int empId, long expenseDate, String expenseDescription) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.empId = empId;
        this.expenseDate = expenseDate;
        this.expenseDescription = expenseDescription;
    }

    public void setAmount(double amount) {
        if(amount > 0)
            this.amount = amount;
        else
            throw new IllegalArgumentException("Expense amounts can not be negative.");
    }
}
