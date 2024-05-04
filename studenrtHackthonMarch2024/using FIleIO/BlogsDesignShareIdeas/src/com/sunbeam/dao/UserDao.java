package com.sunbeam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sunbeam.entity.User;
import com.sunbeam.utils.DbUtil;

public class UserDao implements AutoCloseable {
	private static Connection connection;

	public UserDao() throws SQLException {
		connection = DbUtil.getConnection();
	}

	public void register(User user) throws SQLException {
        String sql = "INSERT INTO user(full_name, email, password, phone, created_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getFull_name());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(user.getCreated_time().getTime()));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
    }
	
	 public static User signIn(String email, String password) throws SQLException {
	        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1, email);
	            preparedStatement.setString(2, password);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                // User with provided email and password exists
	                int id = resultSet.getInt("id");
	                String full_name = resultSet.getString("full_name");
	                String phone = resultSet.getString("phone");
	                Date created_time = resultSet.getDate("created_time");
	      

	                // Create and return the User object
	                User user = new User();
	                user.setId(id);
	                user.setFull_name(full_name);
	                user.setEmail(email);
	                user.setPassword(password);
	                user.setPhone(phone);
	                user.setCreated_time(created_time);
	             

	                return user;
	            } else {
	                // User does not exist with provided credentials
	                return null;
	            }
	        }
	    }
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}
}