package idv.hzm.simpleftp.oneftp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientHandler implements Runnable {
	private Socket clientSocket;
	private String creatTergetPath;
	public ClientHandler(Socket clientSocket, String root) {
		this.clientSocket = clientSocket;
		FileSystem fs = FileSystems.getDefault();
		this.creatTergetPath = root + "\\file_save_terget\\terget" + "\\";
		Path ctp = fs.getPath(creatTergetPath);
		try {
			Files.createDirectory(ctp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			InputStream is = clientSocket.getInputStream();
			BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(creatTergetPath + "\\5.zip"));
			byte[] buffer = new byte[2048];
			int len;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			OutputStream os = clientSocket.getOutputStream();
			os.write("end".getBytes());
			os.close();
			fos.close();
			is.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
