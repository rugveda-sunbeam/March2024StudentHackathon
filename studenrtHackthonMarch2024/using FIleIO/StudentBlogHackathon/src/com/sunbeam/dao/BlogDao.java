package com.sunbeam.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sunbeam.entity.Blog;
import com.sunbeam.entity.Category;
import com.sunbeam.entity.User;

public class BlogDao {
	private static final String CAT_FILE_PATH = "category.txt";

	public void addBlog(Blog blog, String BlogFileName) {
		try (FileWriter writer = new FileWriter(BlogFileName, true)) {
			try (FileWriter writeblog = new FileWriter(blog.getFilename(), true)) {
				SimpleDateFormat sdf = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]");
				String formattedDate = sdf.format(blog.getCreated_time());

				CategoryDAO categoryDAO = new CategoryDAO();
				List<Category> calist = categoryDAO.getAllCategories(CAT_FILE_PATH);
				String cat = null;
				for (Category category : calist) {
					if (blog.getCategory_id() == category.getId())
						cat = category.getTitle();
				}
				// Writing the blog details to the file
				writeblog.write(blog.getId() + "," + blog.getTitle() + ","+blog.getContents()+","+cat+","+formattedDate+"\n");
				writer.write(blog.getId() + "," + blog.getTitle() + "," + cat + "," + blog.getFilename() + ","
						+ blog.getUser_id() + "," + formattedDate + "\n");

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Blog> getAllBlogs(String blogFilePath) {
		List<Blog> blogList = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(blogFilePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int id = Integer.parseInt(parts[0]);
				String title = parts[1];
				String category = parts[2]; // Assuming category is at index 2

				CategoryDAO categoryDAO = new CategoryDAO();
				List<Category> calist = categoryDAO.getAllCategories(CAT_FILE_PATH);
				int categoryId = 0; // Default value if no category is found
				for (Category cat : calist) {
					if (category.equals(cat.getTitle())) {
						categoryId = cat.getId();
						break; // Exit loop once category is found
					}
				}
				String filename = parts[3];
				int userid = Integer.parseInt(parts[4]);
				Date created_time = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]").parse(parts[5]);
				// Create a new Blog object with the retrieved data
				Blog blog = new Blog(id, title, created_time, userid, categoryId, filename);
				blogList.add(blog);
			}
		} catch (IOException | ParseException e) {
			System.out.println("Error occurred while reading blog file: " + e.getMessage());
		}
		return blogList;
	}

// Update Blog
//	public void updateBlog(Blog blog) throws SQLException {
//		// Check if the category exists, if not, insert it
//		int categoryId = blog.getCategory_id();
//		if (!categoryExists(categoryId)) {
//			insertCategory(categoryId, "Default Category"); // Provide a default name or fetch from the blog object
//		}
//
//		String sql = "UPDATE blogs SET title = ?, contents = ?, created_time = ?, user_id = ?, category_id = ? WHERE id = ?";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setString(1, blog.getTitle());
//			ps.setString(2, blog.getContents());
//			ps.setTimestamp(3, new java.sql.Timestamp(blog.getCreated_time().getTime()));
//			ps.setInt(4, blog.getUser_id());
//			ps.setInt(5, blog.getCategory_id());
//			ps.setInt(6, blog.getId());
//			ps.executeUpdate();
//			System.out.println("Blog updated successfully.");
//		}
//	}

//	private boolean categoryExists(int categoryId) throws SQLException {
//		String sql = "SELECT id FROM categories WHERE id = ?";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setInt(1, categoryId);
//			try (ResultSet rs = ps.executeQuery()) {
//				return rs.next();
//			}
//		}
//	}
//
//	private void insertCategory(int categoryId, String categoryName) throws SQLException {
//		String sql = "INSERT INTO categories (id, title, description) VALUES (?, ?, ?)";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setInt(1, categoryId);
//			ps.setString(2, categoryName);
//			ps.setString(3, "Default Description"); // Provide a default description or fetch from the blog object
//			ps.executeUpdate();
//			System.out.println("New category inserted with ID: " + categoryId);
//		}
//	}
//
//	public void deleteBlog(int blogId) throws SQLException {
//		String sql = "DELETE FROM blogs WHERE id = ?";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setInt(1, blogId);
//			int rowsAffected = ps.executeUpdate();
//			if (rowsAffected > 0) {
//				System.out.println("Blog with ID " + blogId + " deleted successfully.");
//			} else {
//				System.out.println("Blog with ID " + blogId + " not found.");
//			}
//		}
//	}
//
//	public List<Blog> searchBlogs(String searchTerm) throws SQLException {
//		List<Blog> searchResults = new ArrayList<>();
//		String sql = "SELECT * FROM blogs WHERE title LIKE ? OR contents LIKE ?";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			// Use %searchTerm% for searching similar strings
//			ps.setString(1, "%" + searchTerm + "%");
//			ps.setString(2, "%" + searchTerm + "%");
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					int id = rs.getInt("id");
//					String title = rs.getString("title");
//					String contents = rs.getString("contents");
//					// Assuming created_time, user_id, and category_id are needed
//					Date created_time = rs.getDate("created_time");
//					int user_id = rs.getInt("user_id");
//					int category_id = rs.getInt("category_id");
//
//					Blog blog = new Blog();
//					blog.setId(id);
//					blog.setTitle(title);
//					blog.setContents(contents);
//					blog.setCreated_time(created_time);
//					blog.setUser_id(user_id);
//					blog.setCategory_id(category_id);
//
//					searchResults.add(blog);
//				}
//			}
//		}
//		return searchResults;
//	}
//
//	// Get Blogs by User ID
//	public List<Blog> getBlogsByUserId(int userId) throws SQLException {
//		List<Blog> userBlogs = new ArrayList<>();
//		String sql = "SELECT * FROM blogs WHERE user_id = ?";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setInt(1, userId);
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					int id = rs.getInt("id");
//					String title = rs.getString("title");
//					String contents = rs.getString("contents");
//					Date created_time = rs.getDate("created_time");
//					int category_id = rs.getInt("category_id");
//
//					Blog blog = new Blog();
//					blog.setId(id);
//					blog.setTitle(title);
//					blog.setContents(contents);
//					blog.setCreated_time(created_time);
//					blog.setUser_id(userId); // Set the user_id
//					blog.setCategory_id(category_id);
//
//					userBlogs.add(blog);
//				}
//			}
//		}
//		return userBlogs;
//	}
//
//
//	public Blog getBlogById(int id) throws SQLException {
//		String sql = "SELECT * FROM blogs WHERE id = ?";
//		try (PreparedStatement ps = connection.prepareStatement(sql)) {
//			ps.setInt(1, id);
//			try (ResultSet rs = ps.executeQuery()) {
//				if (rs.next()) {
//					int blogId = rs.getInt("id");
//					String title = rs.getString("title");
//					String contents = rs.getString("contents");
//					Date updatedTime = rs.getTimestamp("created_time");
//					int userId = rs.getInt("user_id");
//					int categoryId = rs.getInt("category_id");
//
//					Blog blog = new Blog(blogId, title, contents, updatedTime, userId, categoryId);
//					return blog;
//				}
//			}
//		}
//		return null; // Blog not found
//	}

}
