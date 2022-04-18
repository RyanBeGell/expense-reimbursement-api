package dev.begell.entities;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private int expenseId;
    @Setter(AccessLevel.NONE)   //stop the default lambok @data generated setter for amount
    double amount;
    private int empId;
    private boolean approvalStatus;
//    private int approverEmpId;
    private int expenseDate;

    public void setAmount(double amount) {
        if(amount > 0)
            this.amount = amount;
        else
            throw new IllegalArgumentException("Expense amounts can not be negative");
    }
}
