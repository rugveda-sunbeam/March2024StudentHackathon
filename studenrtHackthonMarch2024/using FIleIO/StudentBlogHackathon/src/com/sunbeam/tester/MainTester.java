package com.sunbeam.tester;

import com.sunbeam.entity.User;

public class MainTester {
	public static void main(String[] args) throws Exception {

		int ch;
		do {
			 ch = MenuChoice.menu();
			switch (ch) {
			case 0:
				System.exit(0);
				break;

			case 1:
				User user = UserFunctionality.signIn();
				MenuChoice.mainMenuImpl(user);
				break;

			case 2:
				UserFunctionality.loadLastUserId();
				UserFunctionality.register();
				break;

			case 3:
				UserFunctionality.showAllUsers();
				break;
			default:
				break;
			}
		} while (ch != 0);

	}
}
