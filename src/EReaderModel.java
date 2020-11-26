import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class EReaderModel {
	
	String[] bookName = {"book2.txt","book1.txt"};
	public HashMap<String, Book> bookLibrary = new HashMap<>();
	
	public EReaderModel() {
		readBook();
	}
	
	public void readBook() {
		for(int i = 0;i<bookName.length;i++) {
			Book book = new Book(bookName[i]);
			Library(bookName[i],book);
		}
	}
	
	/**
	 * 
	 * @param book
	 */
	public void Library(String name, Book book) {
		bookLibrary.put(name, book);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Book getBook(String name) {
		return bookLibrary.get(name);
	}
	
	/**
	 * 
	 * @param name
	 */
	public ArrayList<String[]> getLines(String name) {
		Book book = getBook(name);
		ArrayList<String[]> lines = book.lines();
		return lines;
	}

/*
 * 
 */
	

/*
**************************************************************************************
**************************************PRIVATE INNER CLASS*****************************
**************************************************************************************
*/
	
	
	/**
	 * 
	 * @author alihamzanoor
	 *
	 */
	private class Book {
		
		
		private ArrayList<String[]> lines = new ArrayList<>();
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
				File myObj = new File("book1.txt");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					String[] line = data.replaceAll("[\\\r\\\n]+","").trim().split("\\s+");
					lines.add(line);
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
		}
		
		public ArrayList<String[]> lines() {
			return lines;
		}
		
		
	}

}