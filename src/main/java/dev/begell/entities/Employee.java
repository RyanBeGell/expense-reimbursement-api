package dev.begell.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    int employeeId;
//    private String pass;
    private String firstName;
    private String lastName;
//    private String phoneNumber;
//    private String email;
//    private boolean isAdmin;
}
