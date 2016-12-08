package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * �B�zClient�ݪ�Request������C
 * @version
 */
class RequestThread implements Runnable {
	private Socket clientSocket;
	private String message = "";
	String Ip = "";

	public RequestThread(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	public void run() {
		
		System.out.println("��" + clientSocket.getRemoteSocketAddress() + "�s�u�i��!");
		DataInputStream input = null;
		DataOutputStream output = null;
		try {
			input = new DataInputStream(this.clientSocket.getInputStream());
			output = new DataOutputStream(this.clientSocket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (!this.clientSocket.isClosed()) {
			try {
				message = input.readUTF(); // Ū�J�e��server ������
				System.out.println(Ip + " �� Server��:" + message);
				
//				output.writeUTF("sdfasdfasdf"); // �e���
//				output.flush();
				
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(String.format("�s�u���_,%s", clientSocket.getRemoteSocketAddress()));
				try {
					if (input != null)
						input.close();
					if (output != null)
						output.close();
					if (this.clientSocket != null && !this.clientSocket.isClosed())
						this.clientSocket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		}

	}
}	