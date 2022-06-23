package Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class SocketHandler extends Thread {

	Socket socket;
	JTextArea txt;
	BufferedReader bf;
	String sender;
	String receiver;

	public SocketHandler(Socket socket, JTextArea txt, String sender, String receiver) {
		this.socket = socket;
		this.txt = txt;
		this.sender = sender;
		this.receiver = receiver;
		try {
			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				if (socket != null) {
					String msg = "";
					if ((msg = bf.readLine()) != null && msg.length() > 0) {
						txt.append("\n"+receiver+": "+msg);
					}
					sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
