package com.sunbeam.tester;

import java.util.Scanner;

import com.sunbeam.entity.User;

public class MenuChoice {
	static final Scanner scanner = new Scanner(System.in);

	public static int menu() {

		int choice;
		System.out.println("0.Exit");
		System.out.println("1.Login");
		System.out.println("2.Register");
		System.out.println("3. Show All Users");
		System.out.print("Enter your choice: ");
		choice = scanner.nextInt();
		return choice;
	}

	public static int mainMenu(User user) {
		System.out.println("Welcome " + user.getFull_name());
		System.out.println("\n--- Main Menu ---");
		System.out.println("1. Show Category");
		System.out.println("2. Add Categories");
		System.out.println("3. Add Blog");
		System.out.println("4. All Blogs");
		System.out.println("5. My Blogs");
		System.out.println("6. Delete Blog");
		System.out.println("7. Read Blog Contents");
		System.out.println("8. Edit Blog");
		System.out.println("9. Search Blog");
		System.out.println("0. Logout");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		return choice;
	}

	static void mainMenuImpl(User user) {

		while (true) {
			int choice = mainMenu(user);
			switch (choice) {
			case 1:
				BlogFunctionality.showAllCategories();
				break;
			case 2:
				BlogFunctionality.loadLastCatId();
				BlogFunctionality.addCategory();
				break;
			case 3:
				try {
					BlogFunctionality.loadLastblogId();
					BlogFunctionality.addBlog(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					BlogFunctionality.viewAllBlogs();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case 5:
				
				try {
					BlogFunctionality.viewMyBlogs(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					if(BlogFunctionality.deleteBlog(user))
					{	System.out.println("Blog deleted Successfully!!!!");
					
					}
					else
						System.out.println("Blog does not exits which is which written by the user ");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			default:
				break;
			}
		}

	}

	
}
