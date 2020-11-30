import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Book {
	
	private int wordsOnPage = 100;
	private int indexNext = 0;
	private int indexPrev;

	private ArrayList<String> lines = new ArrayList<>();

	/**
	 * 
	 * @param name
	 */
	public Book(String name) {
		readBook(name);
	}

	/**
	 * 
	 * @param name
	 */
	public void readBook(String name) {
		try {
			File myObj = new File(name);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] line = data.replaceAll("[\\\r\\\n]+", "").trim().split("\\s+");
				for(int i = 0;i<line.length;i++) {
					lines.add(line[i]);	
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	public ArrayList<String> lines() {
		return lines;
	}

}
