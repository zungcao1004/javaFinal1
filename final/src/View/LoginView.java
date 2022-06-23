package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controller.Client;
import Interface.IAccountDAO;

public class LoginView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865038214288885880L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;

	/**
	 * Create the frame.
	 */
	public LoginView() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(100, 80, 100, 20);
		contentPane.add(lblNewLabel);

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtUsername.setBounds(210, 80, 120, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPassword.setBounds(100, 111, 100, 20);
		contentPane.add(lblPassword);

		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtPassword.setColumns(10);
		txtPassword.setBounds(210, 111, 120, 20);
		contentPane.add(txtPassword);

		JButton btnLogin = new JButton("Log in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = txtUsername.getText();
					String password = txtPassword.getText();

					IAccountDAO accountDAO = (IAccountDAO) Naming.lookup("rmi://localhost:2/final");
					String result = accountDAO.logIn(username, password);
					System.out.println(result);
					
					if (result.equals("guest log in")) {
						Client.openView(Client.View.MAINVIEW);
						Client.closeView(Client.View.LOGIN);
						JOptionPane.showMessageDialog(null, "Xin Chao " + username);
						
					} else if (result.equals("staff log in")) {
						Client.setStaffName(username);
						Client.openView(Client.View.STAFFVIEW);
						Client.closeView(Client.View.LOGIN);
						JOptionPane.showMessageDialog(null, "Xin Chao " + username);
						
					} else if (result.equals("admin log in")) {
						Client.openView(Client.View.ADMINVIEW);
						Client.closeView(Client.View.LOGIN);
						JOptionPane.showMessageDialog(null, "Xin Chao " + username);

					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(100, 142, 100, 80);
		contentPane.add(btnLogin);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = txtUsername.getText();
					String password = txtPassword.getText();

					IAccountDAO accountDAO = (IAccountDAO) Naming.lookup("rmi://localhost:2/final");
					String result = accountDAO.register(username, password);
					System.out.println(result);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnRegister.setBounds(230, 142, 100, 80);
		contentPane.add(btnRegister);
	}
}
