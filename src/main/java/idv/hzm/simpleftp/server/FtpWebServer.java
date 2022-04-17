package idv.hzm.simpleftp.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import idv.hzm.simpleftp.common.molde.AllFileMolde;
import idv.hzm.simpleftp.common.molde.FileMolde;

public class FtpWebServer {

	private int count = 0;
	private ServerSocket serverSocket = null;
	private String root = null;

	public FtpWebServer(int port) throws IOException {
		this(port, "");
	}

	public FtpWebServer(int port, String root) throws IOException {
		this.serverSocket = new ServerSocket(port);
		this.serverSocket.setReuseAddress(true);
		this.root = new File(root).getCanonicalPath();
	}
	
	public void start() throws IOException {
		while(true) {
			Socket clientSocket = this.serverSocket.accept();
			clientSocket.getInetAddress().getHostAddress();
			count++;
			FtpClientHandler ftpClientHandler = new FtpClientHandler(clientSocket,root, count);
			new Thread(ftpClientHandler).start();
		}
	}

}
