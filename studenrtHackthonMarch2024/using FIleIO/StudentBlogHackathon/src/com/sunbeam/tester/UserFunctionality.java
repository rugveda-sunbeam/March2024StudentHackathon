package com.sunbeam.tester;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.sunbeam.dao.UserDao;
import com.sunbeam.entity.User;

public class UserFunctionality {

	static final Scanner scanner = new Scanner(System.in);
	private static final String FILE_PATH = "users.txt";
	private static int lastUserId = 0;

	public static void loadLastUserId() {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int userId = Integer.parseInt(parts[0]);
				lastUserId = Math.max(lastUserId, userId);
			}
		} catch (IOException e) {
			System.out.println("Error occurred while loading last user ID: " + e.getMessage());
		}
	}

	public static void register() throws SQLException {

		System.out.println("Welcome! Please enter your information to register:");

		int userId = ++lastUserId;
		System.out.print("Full Name: ");
		String fullName = scanner.nextLine();

		System.out.print("Email: ");
		String email = scanner.nextLine();

		System.out.print("Password: ");
		String password = scanner.nextLine();

		System.out.print("Phone: ");
		String phone = scanner.nextLine();

		System.out.print("Address: ");
		String address = scanner.nextLine();

		User user = new User(userId, fullName, email, password, phone, address);
		UserDao userDao = new UserDao();
		userDao.register(user, FILE_PATH);

	}

	public static User signIn() {
		UserDao userDao = new UserDao();

		System.out.println("Sign In:");
		System.out.print("Enter email: ");
		String email = scanner.nextLine();

		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		User user = userDao.signIn(email, password, FILE_PATH);

		if (user != null) {
			System.out.println("Sign-in successful!");
		} else {
			System.out.println("Invalid email or password. Please try again.");
		}

		return user;

	}

	public static void showAllUsers() {
		UserDao userDao = new UserDao();
		SimpleDateFormat sdf = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]");

		List<User> users = UserDao.getAllUsers(FILE_PATH);
		System.out
				.println("-------------------------------------------------------------------------------------------");
		System.out.println("U-ID\tName\t\t\tEmail\t\t\tMobile \t\t Address\t\t Time");
		System.out
				.println("-------------------------------------------------------------------------------------------");

		for (User user : users) {
			String formattedDate = sdf.format(user.getCreated_time());
			System.out.println(user.getId() + "\t" + user.getFull_name() + "\t" + user.getEmail() + "\t"
					+ user.getPhone() + "\t" + user.getAddress() + "\t\t" + formattedDate + "\n");

		}
	}

}
