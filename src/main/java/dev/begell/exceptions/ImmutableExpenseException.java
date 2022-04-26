package dev.begell.exceptions;

public class ImmutableExpenseException extends RuntimeException{
    public ImmutableExpenseException(int id){
        super("The expense with id #[" + id + "] has already been approved and can not be changed or deleted.");
    }
}
