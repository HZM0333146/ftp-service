package idv.hzm.simpleftp.client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import idv.hzm.simpleftp.common.molde.AllFileMolde;
import idv.hzm.simpleftp.common.molde.FileMolde;

public class FtpClient {

	public void start(String ip, int port, String tergetPath, String saveAllFileMoldePath) throws Exception {
		AllFileMolde allFileMolde = getAllFileMolde(tergetPath);
		saveAllFileMoldeSerializable(saveAllFileMoldePath, allFileMolde);
		inputServer(ip, port, saveAllFileMoldePath);
	}

	private List<FileMolde> fileMoldeList = null;

	public AllFileMolde getAllFileMolde(String tergerPath) {
		fileMoldeList = new ArrayList<FileMolde>();
		Path path = Paths.get(tergerPath).toAbsolutePath();
		try {
			Files.walkFileTree(path, new FileTree(new FileTreeOnListener() {

				public void onGetFileListen(FileMolde fileMolde) {
					fileMoldeList.add(fileMolde);
				}

			}));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new AllFileMolde(fileMoldeList);
	}

	public void saveAllFileMoldeSerializable(String savePath, AllFileMolde allFileMolde) {
		try (FileOutputStream fileOutputStream = new FileOutputStream(savePath);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
			objectOutputStream.writeObject(allFileMolde);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void inputServer(String ip, int port, String inputFilePath) throws Exception {
		Socket socket = new Socket(InetAddress.getByName(ip), port);
		OutputStream os = socket.getOutputStream();

		FileInputStream fis = new FileInputStream(inputFilePath);
		byte[] buffer = new byte[1024];
		int len;
		while ((len = fis.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}

		socket.shutdownOutput();

		InputStream is = socket.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer2 = new byte[1024];
		int len2;
		while ((len2 = is.read(buffer2)) != -1) {
			baos.write(buffer2, 0, len2);
		}

		baos.close();
		is.close();
		fis.close();
		os.close();
		socket.close();

	}

}
