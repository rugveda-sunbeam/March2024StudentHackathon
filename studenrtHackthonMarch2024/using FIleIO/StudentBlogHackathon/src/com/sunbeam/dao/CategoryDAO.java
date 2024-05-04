package com.sunbeam.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sunbeam.entity.Category;
import com.sunbeam.entity.User;

public class CategoryDAO implements AutoCloseable {

	public void addCategory(Category category, String filename) {
		SimpleDateFormat sdf = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]");
		String formattedDate = sdf.format(category.getCreated_time());

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
			writer.write(category.getId() + "," + category.getTitle() + "," + category.getDescription() + ","
					+ formattedDate + "\n");
			System.out.println("Category added successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Show all Categories
	public List<Category> getAllCategories(String filename) {
		List<Category> catList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int id = Integer.parseInt(parts[0]);
				String title = parts[1];
				String description = parts[2];
				Date Created_time = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]").parse(parts[3]);
				Category category = new Category(id, title, description, Created_time);
				catList.add(category);
			}
		} catch (IOException | ParseException e) {
			System.out.println("Error occurred while reading users file: " + e.getMessage());
		}
		return catList;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

}
