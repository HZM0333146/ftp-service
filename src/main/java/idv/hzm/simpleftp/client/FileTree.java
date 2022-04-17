package idv.hzm.simpleftp.client;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import idv.hzm.simpleftp.common.molde.FileMolde;

public class FileTree implements FileVisitor<Path> {
	private int count = 0;
	private int rootCount = 0;
	private FileTreeOnListener onGetFileListen;
	public FileTree(FileTreeOnListener onGetFileListen) {
		this.count = 0;
		this.rootCount = 0;
		this.onGetFileListen = onGetFileListen;
	}
	
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		count++;
		if(count == 1) {
			this.rootCount = dir.getNameCount();
		}else {
			getDirFileMolde(dir);
		}
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		count++;
		getFileMolde(file);
		if(attrs.isSymbolicLink()) {
			System.out.println("\t --> " + file.getFileName() + "is SymbolicLink");
		}
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		count++;
		System.out.println(count + ".visitFileFailed: " + file);
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}
	
	private void getDirFileMolde(Path dir) throws IOException {
		Path dirPath = dir.subpath(rootCount, dir.getNameCount());
		FileMolde fileMolde = new FileMolde();
		fileMolde.setType(0);
		fileMolde.setPath(dirPath.toString());
		fileMolde.setContent(null);
		onGetFileListen.onGetFileListen(fileMolde);
	}
	
	private void getFileMolde(Path file) throws IOException {
		Path path = Paths.get(file.toString()).toAbsolutePath();
		Charset ch = Charset.defaultCharset();
		List<String> lines = Files.readAllLines(path, ch);
		Path dirPath = file.subpath(rootCount, file.getNameCount());
		FileMolde fileMolde = new FileMolde();
		fileMolde.setType(1);
		fileMolde.setPath(dirPath.toString());
		fileMolde.setContent(lines);
		onGetFileListen.onGetFileListen(fileMolde);
	}

}
