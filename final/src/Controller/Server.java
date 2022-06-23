package Controller;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

import Interface.DatabaseInfo;
import Interface.IAccountDAO;
import Interface.IProductDAO;
import Model.Product;

public class Server extends UnicastRemoteObject implements IAccountDAO, IProductDAO, DatabaseInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 210480426943398399L;

	static Connection con = null;

	protected Server() throws RemoteException {
	}

	@Override
	public String insert(String name, double price) {
		try {
			con = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.user, DatabaseInfo.password);
			String sql = "insert into product (name, price) values (?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setDouble(2, price);
			ps.execute();

			return "Insert successfully";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "Something wrong with insert process";
	}

	@Override
	public String delete(int id) {
		try {
			con = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.user, DatabaseInfo.password);
			String sql = "delete from product where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();

			return "Delete successfully";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "Something wrong with delete process";
	}

	@Override
	public String update(int id, String name, double price) {
		try {
			con = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.user, DatabaseInfo.password);
			String sql = "update product set name = ?, price = ? where id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setDouble(2, price);
			ps.setInt(3, id);
			ps.execute();

			return "Update successfully";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "Something wrong with update process";
	}

	@Override
	public String readXML() {
		return null;
	}

	@Override
	public String writeXML() {
		return null;
	}

	public static ArrayList<Product> getProducts() {
		ArrayList<Product> products = new ArrayList<>();
		try {
			con = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.user, DatabaseInfo.password);
			String sql = "select * from product";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			Product p;
			while (rs.next()) {
				p = new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3));
				products.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return products;
	}

	@Override
	public DefaultTableModel showAll() {
		DefaultTableModel model = new DefaultTableModel();
		try {

			Object[] columnsName = new Object[3];

			columnsName[0] = "id";
			columnsName[1] = "name";
			columnsName[2] = "price";

			model.setColumnIdentifiers(columnsName);

			Object[] rowData = new Object[4];

			for (int i = 0; i < getProducts().size(); i++) {
				rowData[0] = getProducts().get(i).getId();
				rowData[1] = getProducts().get(i).getName();
				rowData[2] = getProducts().get(i).getPrice();

				model.addRow(rowData);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@Override
	public String register(String username, String password) {
		try {
			con = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.user, DatabaseInfo.password);
			String sql = "insert into accounttbl (username, password) values (?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			String encryptedPassword = Encryption.encrypt(password);
			ps.setString(2, encryptedPassword);
			ps.execute();

			return "register successfully";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "something wrong with register process";
	}

	@Override
	public boolean checkGuestAccount(String username, String password) {
		try {
			con = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.user, DatabaseInfo.password);

			String sql = "select password, role from accounttbl where username = '" + username + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			String decryptedPassword = Decryption.decrypt(rs.getString(1));
			String role = rs.getString(2);

			if (decryptedPassword.equals(password) && role.equals("guest")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkAdminAccount(String username, String password) {
		try {
			con = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.user, DatabaseInfo.password);

			String sql = "select password, role from accounttbl where username = '" + username + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			String decryptedPassword = Decryption.decrypt(rs.getString(1));
			String role = rs.getString(2);

			if (decryptedPassword.equals(password) && role.equals("admin")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkStaffAccount(String username, String password) {
		try {
			con = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.user, DatabaseInfo.password);

			String sql = "select password, role from accounttbl where username = '" + username + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			String decryptedPassword = Decryption.decrypt(rs.getString(1));
			String role = rs.getString(2);
			if (decryptedPassword.equals(password) && role.equals("staff")) {
				return true;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public String logIn(String username, String password) {
		try {
			if (checkGuestAccount(username, password)) {
				return "guest log in";
			}
			if (checkStaffAccount(username, password)) {
				return "staff log in";
			}
			if (checkAdminAccount(username, password)) {
				return "admin log in";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "something wrong with log in process";
	}

	public static String toXMLProduct() {
		return null;
		
	}
	
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(2);
			try {
				Naming.rebind("rmi://localhost:2/final", new Server());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Server ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
}
