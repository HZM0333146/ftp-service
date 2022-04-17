package idv.hzm.simpleftp.common.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileUtil {
	public static void fileReadWriteAllLine(String source, String target) throws IOException {
		Path path = Paths.get(source).toAbsolutePath();
		Charset ch = Charset.defaultCharset();
		List<String> lines = Files.readAllLines(path, ch);
		Path targetPath = Paths.get(target).toAbsolutePath();
		Files.write(targetPath, lines, ch, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.WRITE);
		System.out.println("done...");
	}
}
