import java.util.ArrayList;
import java.util.HashMap;

public class EReaderModel {
	
	public HashMap<String, Book> bookLibrary = new HashMap<>();
	
	/* This field represents the current displayed book in the library */
	private Book curBook;

	         
	public void readBook(String bookName) {
		Book book = new Book(bookName);
		Library(bookName, book);
		curBook = book;
	}
	
	public Book getCurBook() {
		return curBook;
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
	
	/**
	 * Checks if a book is contained in the library model.
	 * 
	 * @param String fileName represents the file name of the book
	 * @return returns true if book is contained in library and false if not
	 */
	public boolean contains(String fileName) {
		if (bookLibrary.containsKey(fileName)) {
			return true;
		}
		return false;
	}
}

