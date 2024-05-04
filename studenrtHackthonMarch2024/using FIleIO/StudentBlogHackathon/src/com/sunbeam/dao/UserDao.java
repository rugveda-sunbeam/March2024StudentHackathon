package com.sunbeam.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sunbeam.entity.User;

public class UserDao {

	public void register(User user, String FILE_PATH) {
		try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
			SimpleDateFormat sdf = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]");
			String formattedDate = sdf.format(user.getCreated_time());

			writer.write(user.getId() + "," + user.getFull_name() + "," + user.getEmail() + "," + user.getPassword()
					+ "," + user.getPhone() + "," + user.getAddress() + "," + formattedDate + "\n");
			System.out.println(
					"-------------------------------------------------------------------------------------------");
			System.out.println("U-ID\tName\t\t\tEmail\t\t\tMobile \t\t Address\t\t Time");
			System.out.println(
					"-------------------------------------------------------------------------------------------");
			System.out.println(user.getId() + "\t" + user.getFull_name() + "\t" + user.getEmail() + "\t\t"
					+ user.getPhone() + "\t" + user.getAddress() + "\t" + formattedDate + "\n");

		} catch (IOException e) {
			System.out.println("Error occurred while saving user: " + e.getMessage());
		}
	}

	public User signIn(String email, String password, String FILE_PATH) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String userEmail = parts[2];
				String userPassword = parts[3];
				if (email.equals(userEmail) && password.equals(userPassword)) {
					int id = Integer.parseInt(parts[0]);
					String full_name = parts[1];
					String phone = parts[4];
					String address = parts[5];
					return new User(id, full_name, email, password, phone, address);
				}
			}
		} catch (IOException e) {
			System.out.println("Error occurred while reading users file: " + e.getMessage());
		}
		return null;
	}

	public static List<User> getAllUsers(String FILE_PATH) {
		List<User> userList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int id = Integer.parseInt(parts[0]);
				String full_name = parts[1];
				String email = parts[2];
				String phone = parts[4];
				String address = parts[5];
				Date Created_time = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]").parse(parts[6]);
				User user = new User(id, full_name, email, phone, address, Created_time);
				userList.add(user);
			}
		} catch (IOException | ParseException e) {
			System.out.println("Error occurred while reading users file: " + e.getMessage());
		}
		return userList;
	}
}
