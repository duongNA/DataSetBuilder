package com.duongna.datasetbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Builder {
	
	private static final String INDEX_FILE_PATH = "/home/letmsee/Desktop/build_data_set/trec06p/full/index";
	private static final String DATASET_DIR_PATH = "/home/letmsee/Desktop/build_data_set/trec06p";
	private static final String DES_DIR_PATH = "/home/letmsee/Desktop/build_data_set/resultSet";
	
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(INDEX_FILE_PATH));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() > 0) {
				// determine email in this spam or ham
				boolean isSpam = false;
				if (line.startsWith("spam")) {
					isSpam = true;
				} else if (line.startsWith("ham")) {
					isSpam = false;
				}
				
				// get relative file path (relative to DATASET_DIR_PATH)
				int pos = line.indexOf("data");
				String relFilePath = line.substring(pos);
				try {
					copyFileToBuildDir(relFilePath, isSpam);
					System.out.println("Complete process file " + relFilePath);
				} catch (Exception e) {
					System.out.println("Couldn't copy file " + relFilePath);
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		System.out.println("Complete!!!");
		reader.close();
	}

	private static void copyFileToBuildDir(String relativeFilePath, boolean isSpam) throws IOException {
		String parts[] = relativeFilePath.split("/");
		
		// build destination file name
		StringBuilder builder = new StringBuilder();
		if (isSpam) {
			builder.append("spam");
		} else {
			builder.append("ham");
		}
		
		for (String part : parts) {
			builder.append("_");
			builder.append(part);
		}
		
		// build destionation file absolute path
		File dir = null;
		if (isSpam) {
			dir = Utils.createDirIfNotExists(DES_DIR_PATH + "/" + "spam");
		} else {
			dir = Utils.createDirIfNotExists(DES_DIR_PATH + "/" + "ham");
		}
		
		String fileAbsolutePath = dir.getAbsolutePath() + "/" + builder.toString();
		
		Utils.copyFile(DATASET_DIR_PATH + "/" + relativeFilePath, fileAbsolutePath);
	}
	
	
	
	
}


