package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

import static java.lang.System.*;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/Hospitalmanagement";
    private static final String username="root";
    private static final String password="Admin@123";

    public static void main(String[] args) {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);
            Doctor doctor=new Doctor(connection);
            while(true){
                System.out.println("Hospital Management System");
                System.out.println("1.Add patient");
                System.out.println("2.View Patients");
                System.out.println("3.View Doctor");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Enter choice");
                int choice= scanner.nextInt();
                switch (choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.ViewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.ViewDOctor();
                        System.out.println();
                        break;
                    case 4:
                        BookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice!!");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void BookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
        System.out.println("Enter Patient Id:");
        int patientid=scanner.nextInt();
        System.out.println("Doctor id");
        int doctorid=scanner.nextInt();
        System.out.println("Enter appointment date");
        String appointmentdate=scanner.next();
        if(patient.getpatient(patientid)&&doctor.getdoctor(doctorid)){
            if(checkDoctorAvailability(doctorid,appointmentdate,connection)){
                String appointmentquery="insert into Appointment(patient_id ,doctor_id,appointment_date) values(?,?,?)";
            try{
                PreparedStatement preparedStatement=connection.prepareStatement(appointmentquery);
                preparedStatement.setInt(1,patientid);
                preparedStatement.setInt(2,doctorid);
                preparedStatement.setString(3,appointmentdate);
                int rowsaffected=preparedStatement.executeUpdate();
                if(rowsaffected>0){
                    System.out.println("appointment book");
                }else{
                    System.out.println("failed to book");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

            }

            else{
                System.out.println("doctor is not available");
            }
        }else{
            out.println("either doctor or patient doesn't exist");
        }

    }
    public static boolean checkDoctorAvailability(int doctorid,String appointmentdate,Connection connection){
        String query="select count(*) from Appointment from where doctor_id=? and appointment_date=?";
        try{
        PreparedStatement preparedStatement=connection.prepareStatement(query);
        preparedStatement.setInt(1,doctorid);
        preparedStatement.setString(2,appointmentdate);
        ResultSet rs=preparedStatement.executeQuery();
        if(rs.next()){
            int count=rs.getInt(1);
            if(count==0){
                return true;
            }else{
                return  false;
            }
        }
    }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
}}
