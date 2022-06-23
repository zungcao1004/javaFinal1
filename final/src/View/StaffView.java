package View;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.SocketHandler;

public class StaffView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3289450319966365837L;
	private JPanel contentPane;
	Socket socket = null;
	String ip = "localhost";
	int port = 1;
	BufferedReader bf = null;
	DataOutputStream os = null;
	SocketHandler socketHandler = null;
	JFrame thisFrame = this;
	

	/**
	 * Create the frame.
	 */
	public StaffView(String staffName) {
		setTitle("StaffView");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		connect(staffName);
	}

	public void connect(String staffName) {
		try {
			socket = new Socket(ip, port);
			if (socket != null) {
				ChatPanel p = new ChatPanel(socket, staffName, "Admin");
				thisFrame.getContentPane().add(p);
				p.getTxtMessages().append("Admin is online");
				p.updateUI();
				
				bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				os = new DataOutputStream(socket.getOutputStream());
				
				os.writeBytes("Staff: "+staffName);
				os.write(13);
				os.write(10);
				os.flush();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
