package View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class AdminView extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6335204145598063366L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	ServerSocket serverSocket = null;
	BufferedReader bf = null;
	Thread t;
	

	/**
	 * Create the frame.
	 */
	public AdminView() {
		setTitle("AdminView");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 634, 445);
		contentPane.add(tabbedPane);

		host();
		t = new Thread(this);
		t.start();
	}
	
	public void host() {
		try {
			serverSocket = new ServerSocket(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				if (socket!= null) {
					bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String str = bf.readLine();
					int pos = str.indexOf(":");
					String staffName = str.substring(pos+1);
					ChatPanel p = new ChatPanel(socket, "Admin", staffName);
					tabbedPane.add(staffName, p);
					p.updateUI();
				}
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
