package Controller;

import View.AdminView;
import View.LoginView;
import View.MainView;
import View.StaffView;

public class Client {
	public static LoginView loginView;
	private static MainView mainView;
	private static AdminView adminView;
	private static StaffView staffView;
	static String staffName = "";
	
	public static void setStaffName(String staffName) {
		Client.staffName = staffName;
	}

	public enum View {
		LOGIN, MAINVIEW, ADMINVIEW, STAFFVIEW
	}
	
	public static void openView(View viewName) {
		if (viewName != null) {
			switch (viewName) {
			case LOGIN:
				loginView = new LoginView();
				loginView.setVisible(true);
				break;
			case MAINVIEW:
				mainView = new MainView();
				mainView.setVisible(true);
				break;
			case ADMINVIEW:
				adminView = new AdminView();
				adminView.setVisible(true);
				break;
			case STAFFVIEW:
				staffView = new StaffView(staffName);
				staffView.setVisible(true);
				break;
			}
		}
	}
	
	public static void closeView(View viewName) {
		if (viewName != null) {
			switch (viewName) {
			case LOGIN:
				loginView.dispose();
				break;
			case MAINVIEW:
				mainView.dispose();
				break;
			case ADMINVIEW:
				adminView.dispose();
				break;
			case STAFFVIEW:
				staffView.dispose();
				break;
			}
		}
	}
	
	public void initView() {
		loginView = new LoginView();
		loginView.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Client().initView();
	}
	
}
