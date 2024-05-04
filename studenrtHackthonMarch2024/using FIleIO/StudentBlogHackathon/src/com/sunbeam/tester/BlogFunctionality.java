package com.sunbeam.tester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.sunbeam.dao.BlogDao;
import com.sunbeam.dao.CategoryDAO;
import com.sunbeam.entity.Blog;
import com.sunbeam.entity.Category;
import com.sunbeam.entity.User;

public class BlogFunctionality {
	static final Scanner scanner = new Scanner(System.in);
	private static final String CAT_FILE_PATH = "category.txt";
	private static final String BLOG_FILE_PATH = "blog.txt";

	private static int lastCatId = 0;
	private static int lastblogId = 0;

	public static void loadLastCatId() {
		try (BufferedReader reader = new BufferedReader(new FileReader(CAT_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int catId = Integer.parseInt(parts[0]);
				lastCatId = Math.max(lastCatId, catId);
			}
		} catch (IOException e) {
			System.out.println("Error occurred while loading last Cat ID: " + e.getMessage());
		}
	}

	public static void loadLastblogId() {
		try (BufferedReader reader = new BufferedReader(new FileReader(BLOG_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int blogId = Integer.parseInt(parts[0]);
				lastblogId = Math.max(lastblogId, blogId);
			}
		} catch (IOException e) {
			System.out.println("Error occurred while loading last blog ID: " + e.getMessage());
		}
	}

	public static void addCategory() {
		int catId = ++lastCatId;
		System.out.println("Enter the title of the new category:");
		scanner.next();
		String title = scanner.nextLine();

		System.out.println("Enter the description of the new category:");
		String description = scanner.nextLine();

		Category category = new Category(catId, title, description);
		CategoryDAO categoryDAO = new CategoryDAO();
		categoryDAO.addCategory(category, CAT_FILE_PATH);

	}

	public static void showAllCategories() {
		CategoryDAO categoryDAO = new CategoryDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]");

		List<Category> catList = categoryDAO.getAllCategories(CAT_FILE_PATH);
		System.out
				.println("-------------------------------------------------------------------------------------------");
		System.out.println("C-ID\tTitle\t\tDescription\t\t\tCREATION Time");
		System.out
				.println("-------------------------------------------------------------------------------------------");

		for (Category category : catList) {
			String formattedDate = sdf.format(category.getCreated_time());
			System.out.println(category.getId() + "\t" + category.getTitle() + "\t" + category.getDescription()
					+ "\t\t\t" + formattedDate + "\n");

		}
	}

	public static void addBlog(User user) throws Exception {
		BlogDao blogDAO = new BlogDao();

		int blogId = ++lastblogId;
		System.out.println("Enter the title of the new blog:");
		scanner.next();
		String title = scanner.nextLine();

		System.out.println("Enter the contents of the new blog:");
		String contents = scanner.nextLine();

		System.out.println("Enter the file Name:");
		String filename = scanner.nextLine();

		int userId = user.getId();

		showAllCategories();

		System.out.println("Enter the category ID for the new blog:");
		int categoryId = scanner.nextInt();

		// Create a new Blog object
		Blog newBlog = new Blog(blogId, title, contents, userId, categoryId, filename);

		// Add the blog to the database
		blogDAO.addBlog(newBlog, BLOG_FILE_PATH);
		System.out.println("Blog added successfully.");

	}

	public static void viewAllBlogs() throws Exception {
		BlogDao blogDAO = new BlogDao();
		SimpleDateFormat sdf = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]");
		List<Blog> bloglist = blogDAO.getAllBlogs(BLOG_FILE_PATH);
		System.out
				.println("-------------------------------------------------------------------------------------------");
		System.out.println("C-ID\tTitle\t\tDescription\t\t\tCREATION Time");
		System.out
				.println("-------------------------------------------------------------------------------------------");

		for (Blog blog : bloglist) {
			String formattedDate = sdf.format(blog.getCreated_time());
			CategoryDAO categoryDAO = new CategoryDAO();

			List<Category> calist = categoryDAO.getAllCategories(CAT_FILE_PATH);
			String catstr = null; // Default value if no category is found
			for (Category cat : calist) {
				if (blog.getCategory_id() == cat.getId()) {
					catstr = cat.getTitle();
					break; // Exit loop once category is found
				}
			}
			System.out.println(blog.getId() + "\t" + blog.getTitle() + "\t" + catstr + "\t" + blog.getFilename() + "\t"
					+ blog.getUser_id() + "\t" + formattedDate + "\n");

		}
	}

	public static void viewMyBlogs(User user) throws Exception {
		BlogDao blogDAO = new BlogDao();
		SimpleDateFormat sdf = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]");
		List<Blog> bloglist = blogDAO.getAllBlogs(BLOG_FILE_PATH);
		System.out
				.println("-------------------------------------------------------------------------------------------");
		System.out.println("C-ID\tTitle\t\tDescription\t\t\tCREATION Time");
		System.out
				.println("-------------------------------------------------------------------------------------------");

		for (Blog blog : bloglist) {
			if (blog.getUser_id() == user.getId()) {
				String formattedDate = sdf.format(blog.getCreated_time());
				CategoryDAO categoryDAO = new CategoryDAO();

				List<Category> calist = categoryDAO.getAllCategories(CAT_FILE_PATH);
				String catstr = null; // Default value if no category is found
				for (Category cat : calist) {
					if (blog.getCategory_id() == cat.getId()) {
						catstr = cat.getTitle();
						break; // Exit loop once category is found
					}
				}
				System.out.println(blog.getId() + "\t" + blog.getTitle() + "\t" + catstr + "\t" + blog.getFilename()
						+ "\t" + blog.getUser_id() + "\t" + formattedDate + "\n");
			}
		}
	}

	public static boolean deleteBlog(User user) {
		try {
			BlogDao blogDAO = new BlogDao();
			SimpleDateFormat sdf = new SimpleDateFormat("[d/M/yyyy-HH:mm:ss]");

			List<Blog> blogList = blogDAO.getAllBlogs(BLOG_FILE_PATH);

			// Display only the blogs belonging to the logged-in user
			System.out.println("Your Blogs:");
			for (Blog blog : blogList) {
				if (blog.getUser_id() == user.getId()) {
					System.out.println(blog.getId() + ": " + blog.getTitle());
				}
			}

			// Prompt user for blog ID to delete
			System.out.println("Enter the ID of the blog you want to delete:");
			int blogIdToDelete = scanner.nextInt();

			// Iterate over the blog list to find and delete the blog
			Iterator<Blog> iterator = blogList.iterator();
			while (iterator.hasNext()) {
				Blog blog = iterator.next();
				if (blog.getId() == blogIdToDelete && blog.getUser_id() == user.getId()) {
					CategoryDAO categoryDAO = new CategoryDAO();
					String formattedDate = sdf.format(blog.getCreated_time());
					List<Category> calist = categoryDAO.getAllCategories(CAT_FILE_PATH);
					String catstr = null; // Default value if no category is found
					for (Category cat : calist) {
						if (blog.getCategory_id() == cat.getId()) {
							catstr = cat.getTitle();
							break; // Exit loop once category is found
						}
					}
					iterator.remove();
					// Write the updated blog list back to the file
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(BLOG_FILE_PATH))) {
						for (Blog bl : blogList) { // Use bl instead of blog
							writer.write(bl.getId() + "," + bl.getTitle() + "," + catstr + "," + bl.getFilename()
							+ "," + blog.getUser_id() + "," + formattedDate + "\n"); 
						}
					}
					System.out.println("Blog deleted successfully.");
					return true;
				}
			}
			System.out.println("Blog not found or you are not authorized to delete it.");
			return false;
		} catch (IOException e) {
			System.out.println("Error occurred while deleting the blog: " + e.getMessage());
			return false;
		}
	}
	
	
	

//

//
//	public static void updateBlog() throws Exception {
//		try (BlogDao blogDao = new BlogDao()) {
//
//			System.out.println("Enter the ID of the blog you want to update:");
//			int blogId = scanner.nextInt();
//			scanner.nextLine(); // Consume newline
//
//			// Retrieve the current blog information
//			Blog currentBlog = blogDao.getBlogById(blogId);
//			if (currentBlog == null) {
//				System.out.println("Blog with ID " + blogId + " not found.");
//				return;
//			}
//
//			System.out.println("Current Title: " + currentBlog.getTitle());
//			System.out.println("Current Contents: " + currentBlog.getContents());
//
//			System.out.println("Enter the new title (or press Enter to keep current title):");
//			String newTitle = scanner.nextLine();
//			if (newTitle.isEmpty()) {
//				newTitle = currentBlog.getTitle(); // Keep current title
//			}
//
//			System.out.println("Enter the new contents (or press Enter to keep current contents):");
//			String newContents = scanner.nextLine();
//			if (newContents.isEmpty()) {
//				newContents = currentBlog.getContents(); // Keep current contents
//			}
//
//			// Update the blog with new title, contents, and updated_time
//			Blog updatedBlog = new Blog();
//			updatedBlog.setId(blogId);
//			updatedBlog.setTitle(newTitle);
//			updatedBlog.setContents(newContents);
//			// Keep current created_time
//			updatedBlog.setCreated_time(new Date()); // Set updated_time to current time
//
//			// For user_id and category_id, you may get from user input or assume a value
//			// For demonstration, assuming user_id and category_id as 1
//			updatedBlog.setUser_id(currentBlog.getUser_id()); // Keep current user_id
//			updatedBlog.setCategory_id(currentBlog.getCategory_id()); // Keep current category_id
//
//			// Update the blog
//			blogDao.updateBlog(updatedBlog);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//

//

}
