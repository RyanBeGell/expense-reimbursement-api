package dev.begell.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    int employeeId;
    String pass;
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    boolean isAdmin;
}
