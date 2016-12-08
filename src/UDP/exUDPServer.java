package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class exUDPServer implements Runnable {
	int port; // �s����
	DatagramSocket socket;

	public exUDPServer(int pPort) {
		port = pPort; // �]�w�s����C
	}

	public void run() {
		final int SIZE = 8192; // �]�w�̤j���T���j�p�� 8192.
		byte buffer[] = new byte[SIZE]; // �]�w�T���Ȧs��
		for (int count = 0;; count++) {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);			
			try {
				socket = new DatagramSocket(port);
				socket.receive(packet); // �����ʥ]�C
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // �]�w������ UDP Socket.
			
			String msg = new String(buffer, 0, packet.getLength()); // �N�����T���ഫ���r��C
			System.out.println(count + " : receive = " + msg); // �L�X�����쪺�T���C
			socket.close(); // ���� UDP Socket.
		}
	}

}
