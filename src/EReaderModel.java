import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;

import javafx.scene.control.MenuItem;

public class EReaderModel {
	
	public HashMap<String, Book> bookLibrary = new HashMap<>();
	
	/* This field represents the current displayed book in the library */
	private Book curBook;

	         
	public void readBook(String bookName) {
		Book book = new Book(bookName);
		Library(bookName, book);
		curBook = book;
	}
	
	private void loadLibrary() throws Exception {
		HashMap<String, Integer> bookList = new HashMap<String, Integer>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("BookLibrary.txt"));
			String line = bufferedReader.readLine();
			while(line != null) {
				String[] book = line.split("s3p4r4t0r");
				readBook(book[0]);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (FileNotFoundException f) {
			try {
				File newLibrary = new File("BookLibrary.txt");
				System.out.println(newLibrary.canRead());
				newLibrary.createNewFile();
			} catch (IOException g){
				g.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String bookName : bookList.keySet()) {
			//library.getItems().add(new MenuItem(bookName));
		}
	}
	
	/**
	 * This method is used to interact with the current displayed book
	 * in the library and turn its current page to the first one.
	 * 
	 * @param fileName represents the book to open
	 */
	public void openBook(String fileName) {
		
		if (bookLibrary.containsKey(fileName)) {
			Book newBook = bookLibrary.get(fileName);
			curBook = newBook;
			curBook.openBook();
		} else {
			Book newBook = new Book(fileName);
			bookLibrary.put(fileName, newBook);
			curBook = newBook;
			curBook.openBook();
		}
	}
	
	public double getProgress() {
		return curBook.getProgress();
	}
	/*
	 * check if the book found or not 
	 * @return return true if the book is spelled wrong and not valid 
	 */
	public boolean bookNotFounds() {/////////////////////
		return curBook.bookNotFound();
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
	public ArrayList<String> getLines(String name) {
		Book book = getBook(name);
		ArrayList<String> lines = book.lines();
		return lines;
	}
	
	public HashMap<String, Book> getLibrary(){
		return bookLibrary;
	}
	
	/**
	 * This method is used to interact with the current displayed book
	 * in the library and turn its current page to the next one.
	 */
	public void getNextPage() {
		curBook.nextPage();
	}
	
	/**
	 * This method is used to interact with the current displayed book
	 * in the library and turn its current page to the previous one.
	 */
	public void getPrevPage() {
		curBook.prevPage();
	}
}

