package idv.hzm.simpleftp;

import idv.hzm.simpleftp.client.FtpClient;

public class FtpClientMain {

	public static void main(String[] args) throws Exception {
		FtpClient simpleFtpClient = new FtpClient();
		String terget = "C:\\Users\\guang\\workspace\\sideproject-workspace\\simple-ftp-workspace\\client\\terget";
		String temp = "C:\\Users\\guang\\workspace\\sideproject-workspace\\simple-ftp-workspace\\client\\temp\\AllFileMolde.txt";
		simpleFtpClient.start("127.0.0.1", 9100, terget, temp);
	}

}
