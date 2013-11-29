package com.duongna.datasetbuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TrainTestSetBuilder {
	
	private static final String DATA_SET_DIR = "/home/letmsee/Desktop/build_data_set/resultSet";
	private static final String TRAIN_SET_DIR = "/home/letmsee/Desktop/build_data_set/trainSet";
	private static final String TEST_SET_DIR = "/home/letmsee/Desktop/build_data_set/testSet";
	
	public static void main(String[] args) throws IOException {
		System.out.println("Start dividing spam emails => train, test set");
		divideFiles(DATA_SET_DIR + "/" + "spam", true);
		System.out.println("Complete!!!");
		
		System.out.println("Start dividing ham emails => train, test set");
		divideFiles(DATA_SET_DIR + "/" + "ham", false);
		System.out.println("Complete!!!");
	}
	
	
	/**
	 * divides files => train, set with ratio 7:3 
	 * @param dirPath
	 * @param isSpam
	 * @throws IOException 
	 */
	private static void divideFiles(String dirPath, boolean isSpam) throws IOException {
		File dir = new File(dirPath);
		String[] fileNames = Utils.getFileNames(dir.listFiles());
		
		int numOfFiles = fileNames.length;
		
		File trainSetDir = null;
		File testSetDir = null;
		String typeName = null;
		if (isSpam) {
			trainSetDir = Utils.createDirIfNotExists(TRAIN_SET_DIR + "/spam");
			testSetDir = Utils.createDirIfNotExists(TEST_SET_DIR + "/spam");
			typeName = "spam";
		} else {
			trainSetDir = Utils.createDirIfNotExists(TRAIN_SET_DIR + "/ham");
			testSetDir = Utils.createDirIfNotExists(TEST_SET_DIR + "/ham");
			typeName = "ham";
		}
		
		// copy all files from the data set to train set
		System.out.println("Copying " + fileNames.length + " " + typeName + " email(s) from data set to train set...");
		for (int i = 0; i < fileNames.length; i++) {
			System.out.println("Copying file " + (i+1) + ": " + fileNames[i]);
			Utils.copyFile(dirPath + "/" + fileNames[i], trainSetDir.getAbsolutePath() + "/" + fileNames[i]);
		}
		System.out.println("Complete copying!!!");
		
		
		int numFilesInTest = numOfFiles * 10 / 100;
		List<String> fileAlreadyInTest = new LinkedList<String>();

		System.out.println("Moving " + numFilesInTest + " emails from train set to test set...");
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < numFilesInTest; i++) {
			
			int fileIndex = 0;
			do {
				fileIndex = random.nextInt(numOfFiles);
			} while (fileAlreadyInTest.contains(fileNames[fileIndex]));
			fileAlreadyInTest.add(fileNames[fileIndex]);
			
			System.out.println("Move file " + (i+1) + ": " + fileNames[fileIndex]);
			Utils.moveFile(trainSetDir.getAbsolutePath() + "/" + fileNames[fileIndex], testSetDir.getAbsolutePath() + "/" + fileNames[fileIndex]);
		}
		System.out.println("Complete moving!!!");
				
	}
	
}
