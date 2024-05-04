package com.sunbeam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sunbeam.entity.Category;
import com.sunbeam.utils.DbUtil;

public class CategoryDAO implements AutoCloseable {

	private Connection connection;

	public CategoryDAO() throws SQLException {
		this.connection = DbUtil.getConnection();
	}

	public void addCategory(Category category) throws SQLException {
		String sql = "INSERT INTO categories (title, description) VALUES (?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, category.getTitle());
			ps.setString(2, category.getDescription());
			ps.executeUpdate();
			System.out.println("Category added successfully.");
		}
	}

	// Show all Categories
	public List<Category> getAllCategories() throws SQLException {
		List<Category> categories = new ArrayList<>();
		String sql = "SELECT * FROM categories";
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("description");
				Category category = new Category();
				category.setId(id);
				category.setTitle(title);
				category.setDescription(description);
				categories.add(category);
			}
		}
		return categories;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

}
