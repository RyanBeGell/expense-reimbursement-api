package dev.begell.data;

import dev.begell.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import dev.begell.entities.Employee;
import dev.begell.utilities.*;
import java.sql.*;

@Log4j2
public class EmployeeDAOPostgresImpl implements EmployeeDAO {
    public Employee createEmployee(Employee employee) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into employee values(default,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt("employee_id");
            employee.setEmployeeId(generatedId);
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

    public Employee getEmployeeById(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from employee where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Employee employee = new Employee();
            try {
                if (rs.next()) {    //if there is a first record
                    employee.setEmployeeId((rs.getInt("employee_id")));
                    employee.setFirstName((rs.getString("first_name")));
                    employee.setLastName((rs.getString("last_name")));
                }
                else
                    throw new ResourceNotFoundException(id);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                return null;
            }
            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

    public Employee updateEmployee(Employee employee) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update employee set first_name = ?, last_name = ? where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getEmployeeId());
            int rowsUpdated = ps.executeUpdate();

            try {
                if(rowsUpdated == 0){
                    throw new ResourceNotFoundException(employee.getEmployeeId());
                }
            }catch(ResourceNotFoundException e){
                e.printStackTrace();
                log.error(e.getMessage());
                return null;
            }

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean deleteEmployeeById(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from employee where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return  true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }
}
