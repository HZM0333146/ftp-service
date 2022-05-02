package idv.hzm.simpleftp.oneftp;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import idv.hzm.simpleftp.server.FtpClientHandler;

public class Server {
	private int count = 0;
	private ServerSocket serverSocket = null;
	private String root = null;

	public Server(int port) throws IOException {
		this(port, "");
	}

	public Server(int port, String root) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.serverSocket.setReuseAddress(true);
		this.root = new File(root).getCanonicalPath();
	}
	
	public void start() throws IOException {
		while(true) {
			Socket clientSocket = this.serverSocket.accept();
			clientSocket.getInetAddress().getHostAddress();
			count++;
			ClientHandler clientHandler = new ClientHandler(clientSocket,root);
			new Thread(clientHandler).start();
		}
	}
	public static void main(String[] args) {
		try {
			new Server(9300).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

	}

}
