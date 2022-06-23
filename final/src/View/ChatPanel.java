package View;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.SocketHandler;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ChatPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7699305243218897565L;
	private JTextField txtMessage;
	private Socket socket;
	private JTextArea txtMessages;
	String sender;
	String receiver;
	BufferedReader bf;
	DataOutputStream os;
	SocketHandler t = null;

	/**
	 * Create the panel.
	 */
	public ChatPanel(Socket socket, String sender, String receiver) {
		setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 327, 456, 61);
		add(scrollPane_1);

		txtMessage = new JTextField();
		scrollPane_1.setViewportView(txtMessage);
		txtMessage.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtMessage.getText().trim().length() == 0)
					return;
				try {
					os.writeBytes(txtMessage.getText());
					os.write(13);
					os.write(10);
					os.flush();
					txtMessages.append("\n" + sender + ": " + txtMessage.getText());
					txtMessage.setText("");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnSend.setBounds(476, 327, 148, 61);
		add(btnSend);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 614, 305);
		add(scrollPane);

		txtMessages = new JTextArea();
		scrollPane.setViewportView(txtMessages);

		this.socket = socket;
		this.sender = sender;
		this.receiver = receiver;
		try {
			bf = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			os = new DataOutputStream(this.socket.getOutputStream());
			t = new SocketHandler(socket, txtMessages, sender, receiver);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JTextArea getTxtMessages() {
		return txtMessages;
	}

}
