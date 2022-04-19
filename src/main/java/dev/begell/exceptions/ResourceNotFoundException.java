package dev.begell.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(int id){
        super("The resource with id " + id+ " was not found");
    }
}
