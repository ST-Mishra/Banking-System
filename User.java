package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
        private Connection connection;
        private Scanner scanner;

        public User(Connection connection, Scanner scanner ){
            this.connection = connection;
            this.scanner = scanner;

        }

        public void register(){
            scanner.nextLine();
            System.out.print("Full name: ");
            String full_name  = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if(exist_user(email)){
                System.out.println("Banking_system.User already exist for this Email Address");
                return;
            }
            try{
                String register_query = "INSERT INTO user(full_name, email, password) VALUES(?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(register_query);
                preparedStatement.setString(1, full_name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                int affectedRow = preparedStatement.executeUpdate();

                if(affectedRow>0){
                    System.out.println("Registration successfully");
                } else{
                    System.out.println("Registration failed");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }

        }

        public String login(){
            scanner.nextLine();
            System.out.print("Email:");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            String login_query = "SELECT * FROM `user` WHERE email = ? AND password = ?";

            try{
                PreparedStatement preparedStatement = connection.prepareStatement(login_query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    return email;
                } else{
                    return null;
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        }

        public boolean exist_user(String email){
            String query = "SELECT * FROM user WHERE email = ?";

            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet result = preparedStatement.executeQuery();

                if(result.next()){
                    return true;
                } else{
                    return false;
                }

            } catch(SQLException e){
                e.printStackTrace();
            }
            return false;
        }
    }


