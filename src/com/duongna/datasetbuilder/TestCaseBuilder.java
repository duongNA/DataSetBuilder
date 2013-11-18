package com.duongna.datasetbuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TestCaseBuilder {
	
	private static final String TEST_SET_DIR = "/home/letmsee/Desktop/build_data_set/testSet";
	private static final String TEST_CASE_DIR = "/home/letmsee/Desktop/build_data_set/TestCases";
	
	private static final int NUM_OF_TESTCASES = 1;
	private static final int NUM_SPAM_PER_CASE = 2500;
	private static final int NUM_HAM_PER_CASE = 2500;
	
	public static void main(String[] args) throws IOException {
		String testCaseName;
		for (int i = 0; i < NUM_OF_TESTCASES; i++) {
			testCaseName = "TestCase" + (i + 1);
			File testCaseDir = Utils.createDirIfNotExists(TEST_CASE_DIR + "/" + testCaseName);
			getFilesForTestCase(TEST_SET_DIR + "/spam", testCaseDir.getAbsolutePath(), true);
			getFilesForTestCase(TEST_SET_DIR + "/ham", testCaseDir.getAbsolutePath(), false);
		}
	}
	
	private static void getFilesForTestCase(String emailsDirPath, String testCaseDir, boolean isSpam) throws IOException {
		File emailDir = new File(emailsDirPath);
		String[] fileNames = Utils.getFileNames(emailDir.listFiles());
		
		Random random = new Random(System.currentTimeMillis());
		String emailType;
		int numEmailToGet;
		if (isSpam) {
			numEmailToGet = Math.min(fileNames.length, NUM_SPAM_PER_CASE);
			emailType = "spam";
		} else {
			numEmailToGet = Math.min(fileNames.length, NUM_HAM_PER_CASE);
			emailType = "ham";
		}
		
		
		System.out.println("Getting " + numEmailToGet + " " + emailType + " email(s)");
		List<String> fileNameAlreadyGet = new LinkedList<String>();
		for (int i = 0; i < numEmailToGet; i++) {
			int fileIndex;
			do {
				fileIndex = random.nextInt(fileNames.length);
			} while (fileNameAlreadyGet.contains(fileNames[fileIndex]));
			
			fileNameAlreadyGet.add(fileNames[fileIndex]);
			System.out.println("Getting " + emailType + " email " + (i+1) + ": " + fileNames[fileIndex]);
			Utils.copyFile(emailDir.getAbsolutePath() + "/" + fileNames[fileIndex], testCaseDir + "/" + fileNames[fileIndex]);
		}
		System.out.println("Complete!!!");
		
	}
}
