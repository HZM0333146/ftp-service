package idv.hzm.simpleftp;

import idv.hzm.simpleftp.server.FtpWebServer;

public class FtpSeverMain {

	public static void main(String[] args) throws Exception {
		new FtpWebServer(9100).start();;
	}
}
