package HospitalManagementSystem;

import javax.swing.event.TreeExpansionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    //we make private connection in driver class
    //connection is used to establish connection with driver
    private Connection connection;
    //scanner is also used to take input from user
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.println("Enter patient name: ");
        String patient_name = scanner.next();
        System.out.println("Age of patient: ");
        int patient_age = scanner.nextInt();
        System.out.println("Gender of patient: ");
        String patient_gender = scanner.next();
        try {
            String query = "insert into Patient(patient_name,patient_age,patient_gender)values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patient_name);
            preparedStatement.setInt(2, patient_age);
            preparedStatement.setString(3, patient_gender);
            int affectedrows = preparedStatement.executeUpdate();
            if (affectedrows > 0) {
                System.out.println("patient added successfully");
            } else {
                System.out.println("patient is not added");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ViewPatient() {
        String query = "select * from patient";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("patients");
            System.out.println("+-------+------------+-------+----------+");
            System.out.println("| id    |    Name    | Age   |  Gender   |");
            System.out.println("+-------+------------+-------+----------+");
            while (rs.next()) {
                int id = rs.getInt("id");
                String Name = rs.getString("patient_name");
                int Age = rs.getInt("patient_age");
                String Gender = rs.getString("patient_gender");
                System.out.printf("|%-7s|%-13s|%-6s|%-12s|\n",id, Name,Age, Gender);
                System.out.println("+-------+------------+-------+----------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getpatient(int id) {
        String query = "select * from patients where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
return false;
    }}
