package Interface;

import java.rmi.Remote;

public interface IAccountDAO extends Remote{
	public String register(String username, String password) throws Exception;
	public String logIn(String username, String password) throws Exception;
	public boolean checkGuestAccount(String username, String password) throws Exception;
	public boolean checkAdminAccount(String username, String password) throws Exception;
	public boolean checkStaffAccount(String username, String password) throws Exception;
}
