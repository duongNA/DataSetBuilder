package com.duongna.datasetbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
	public static void copyFile(String sourceFilePath, String desFilePath) throws IOException {
		InputStream in;
		OutputStream out;

		in = new FileInputStream(sourceFilePath);
		out = new FileOutputStream(desFilePath);
		
		byte buffer[] = new byte[1024];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		
		in.close();
		out.close();
	}
	
	public static void moveFile(String srcPath, String desPath) {
		File file = new File(srcPath);
		file.renameTo(new File(desPath));
	}
	
	public static File createDirIfNotExists(String dirPath) throws IOException {
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		return dir;
	}
	
	public static int getNumOfFiles(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			return 0;
		}
		
		File[] files = dir.listFiles();
		int numOfFiles = 0;
		for (File f : files) {
			if (f.isFile()) {
				numOfFiles++;
			}
		}
		
		return numOfFiles;
	}
	
	public static String[] getFileNames(File[] files) {
		String[] fileNames = new String[files.length];
		for (int i = 0; i < fileNames.length; i++) {
			fileNames[i] = files[i].getName(); 
		}
		return fileNames;
	}
}
