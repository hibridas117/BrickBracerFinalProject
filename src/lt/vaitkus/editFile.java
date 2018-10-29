package lt.vaitkus;

import java.io.File;
import java.io.FileWriter;
import java.util.Formatter;

public class editFile {

	private File fileToEdit;
	private Formatter x;

	public void openFile(String fileName) {
		try {
			x = new File(fileName);
		} catch (Exception e) {
			System.out.println("Error");
		}
	}
	
	public void addRecords(String name, int score) {
		x.format("%s%s", name, score);
	}

	public void addRecords1(String filename) {
		fileToEdit = new File(filename);
		try {
			if (!fileToEdit.exists()) {
				System.out.println("We had to make a new file.");
				fileToEdit.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(fileToEdit, true);
		//	fileToEdit.forma
		} catch (Exception e) {
			System.out.println("COULD NOT LOG!!");
		}
	}
	
	public void closeFile( ) {
		x.close();
	}
}
