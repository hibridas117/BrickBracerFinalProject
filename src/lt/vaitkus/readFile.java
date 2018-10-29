package lt.vaitkus;

import java.io.*;
import java.util.*;



public class readFile {

	private Scanner x;
	//public int[] scoresArray;

	public void openFile(String fileName) {
		try {
			x = new Scanner(new File(fileName));
		} catch (Exception e) {
			System.out.println("could not finde file");
		}
	}

	public void readFile() {
		while (x.hasNext()) {
			String a = x.next();
			String score = x.next();
			System.out.printf("%s %s\n", a, score);
		}
		while (x.hasNextInt()) {

		}
	}

	public static int[] storeIntegersfromFileToArray (String file) {
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			int count = 0;
			while (s.hasNextInt()) {
				count++;
				s.nextInt();
			}
			int[] scoresArray = new int[count];
			Scanner s1 = new Scanner(f);
			
			for (int i = 0; i < scoresArray.length;i++) {
				scoresArray[i] =s1.nextInt();
			}
			
			return scoresArray;
		}
		catch (Exception e) {
			return null;
		}
		
		
	}

	public void closeFile() {
		x.close();
	}
}
