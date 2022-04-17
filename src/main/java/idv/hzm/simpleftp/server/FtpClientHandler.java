package idv.hzm.simpleftp.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
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

public class FtpClientHandler implements Runnable {

	private Socket clientSocket;
	private String creatTergetPath;
	private String dateTempSavePath;

	public FtpClientHandler(Socket clientSocket, String root, int id) {
		this.clientSocket = clientSocket;
		FileSystem fs = FileSystems.getDefault();
		this.creatTergetPath = root + "\\file_save_terget\\terget_" + id + "\\";
		Path ctp = fs.getPath(creatTergetPath);
		try {
			Files.createDirectory(ctp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dateTempSavePath = root + "\\data_save_temp\\" + id + ".text";
		Path dts = fs.getPath(dateTempSavePath);
		try {
			Files.createFile(dts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			saveFile(dateTempSavePath, clientSocket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AllFileMolde allFileMolde = getAllFileMolde(dateTempSavePath);
		try {
			createFile(creatTergetPath, allFileMolde);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createFile(String terget, AllFileMolde allFileMolde) throws IOException {
		List<FileMolde> fileMoldeList = allFileMolde.getFileMoldeList();
		FileSystem fs = FileSystems.getDefault();
		for (FileMolde fileMolde : fileMoldeList) {
			Path path = fs.getPath(terget + "\\" + fileMolde.getPath());
			if (fileMolde.getType() == 0) {
				Files.createDirectory(path);
			} else {
				Charset ch = Charset.defaultCharset();
				Path filePath = Files.createFile(path).toAbsolutePath();
				Files.write(filePath, fileMolde.getContent(), ch, StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
			}
		}

	}

	private AllFileMolde getAllFileMolde(String saveFilePath) {
		try (FileInputStream fileInputStream = new FileInputStream(saveFilePath);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
			return (AllFileMolde) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void saveFile(String saveFilePath, Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream(saveFilePath);
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) != -1) {
			fos.write(buffer, 0, len);
		}
		OutputStream os = socket.getOutputStream();
		os.write("end".getBytes());
		os.close();
		fos.close();
		is.close();
		socket.close();
	}

}
