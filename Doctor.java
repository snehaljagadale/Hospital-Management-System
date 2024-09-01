package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    //scanner is also used to take input from user


    public Doctor(Connection connection) {
        this.connection = connection;

    }


    public void ViewDOctor() {
        String query = "select * from doctor";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("patients");
            System.out.println("+-------+------------+-----------------+");
            System.out.println("| id    |    Name    |specialization  |");
            System.out.println("+-------+------------+----------------+");
            while (rs.next()) {
                int id = rs.getInt("id");
                String Name = rs.getString("doctor_name");
                String specialization = rs.getString("specilization");
                System.out.printf("|%-7s|%-13s|%-18s|\n",id,Name,specialization);
                System.out.println("+-------+------------+----------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getdoctor(int id) {
        String query = "select * from doctor where id=?";
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
    }
}
