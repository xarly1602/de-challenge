package com.walmart.org.utils;

import java.sql.*;

public class DbUtils {
    private final static String jdbcdriver = "org.h2.Driver";
    private final static String db_url = "jdbc:h2:mem:gamesdb";

    public static Long insertCompany(String name){
        String query = "insert into company (name) values (?)";
        try {
            Connection connection = dbConnect();
            PreparedStatement statement = connection.prepareStatement("select * from company where name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                PreparedStatement preparedStmt = connection.prepareStatement(query, new String[]{"id"});
                preparedStmt.setString (1, name);
                preparedStmt.execute();
                ResultSet keys = preparedStmt.getGeneratedKeys();
                keys.next();
                Long key = keys.getLong(1);
                connection.close();
                return key;
            } else {
                return resultSet.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static void insertConsole(String name, Long company){
        String query = "insert into console (name, company) values (?,?)";
        try {
            Connection connection = dbConnect();
            PreparedStatement preparedStmt = connection.prepareStatement(query, new String[]{"id"});
            preparedStmt.setString (1, name);
            preparedStmt.setLong (2, company);
            preparedStmt.execute();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertRank(int metascore, String name, String console, String userscore, String date){
        String query = "insert into rank (metascore,name,console,userscore,date) values (?,?,?,?,?)";
        try {
            Connection connection = dbConnect();
            Long consoleId = findConsoleByName(console, connection);
            PreparedStatement preparedStmt = connection.prepareStatement(query, new String[]{"id"});
            preparedStmt.setInt (1, metascore);
            preparedStmt.setString (2, name);
            preparedStmt.setLong (3, consoleId);
            preparedStmt.setString (4, userscore);
            preparedStmt.setString (5, date);
            preparedStmt.execute();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long findConsoleByName(String name, Connection connection){
        try {
            PreparedStatement statement = connection.prepareStatement("select * from console where name = ?");
            statement.setString (1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return resultSet.getLong(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private static Connection dbConnect() {
        try {
            Class.forName(jdbcdriver);
            return DriverManager.getConnection(db_url,"sa","password");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
