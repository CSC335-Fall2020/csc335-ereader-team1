import java.util.ArrayList;
import java.util.HashMap;

/**
 * Model class for EReader, controls underlying structure for implementation. 
 * @author Ali Hamza
 * @author Sultan Alnhari
 */
public class EReaderModel {
	
	public HashMap<String, Book> bookLibrary = new HashMap<>();
	
	/* This field represents the current displayed book in the library */
	private Book curBook;

	/**
	 * Opens a new book, adds it to the Library, and sets the new book to be current.
	 * @author Ali Hamza 
	 * @author Sultan Alnhari 
	 * @param bookName, new name of book or path to book. 
	 */
	public void readBook(String bookName) {
		Book book = new Book(bookName);
		Library(bookName, book);
		curBook = book;
	}
	
	/**
	 * This method is used to interact with the current displayed book
	 * in the library and turn its current page to the first one.
	 * @author Ali Hamza 
	 * @author Sultan Alnhari 
	 * @param fileName, represents the book to open. 
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
	
	/**
	 * Getter method for the current book's progress (for progress bar).
	 * @author Ali Hamza
	 * @return double value in range of 0.0-1.0 that represents overall progress 
	 */
	public double getProgress() {
		return curBook.getProgress();
	}
	/** 
	 * @author Sultan Alnhari 
	 * Checks if the book is found or not. 
	 * @return return true if the book is spelled wrong and not valid. 
	 */
	public boolean bookNotFounds() {
		return curBook.bookNotFound();
	}
	
	/**
	 * Adds a new book to the library for this class. 
	 * Uses the name of the book as a key and Book itself as value. 
	 * @author Ali Hamza
	 * @author Sultan Alnhari
	 * @param name, String name of book to add. 
	 * @param book, Book object of book to add. 
	 */
	public void Library(String name, Book book) {
		bookLibrary.put(name, book);
	}
	
	/**
	 * Returns the Book object that name represents. 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari 
	 * @param name, String name of Book to retrieve. 
	 * @return Book object matching name
	 */
	public Book getBook(String name) {
		return bookLibrary.get(name);
	}
	
	/**
	 * Gets an ArrayList of all words in a Book specified by name. 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari 
	 * @param name, String name of book to find. 
	 * @return complete ArrayList of all words in the book. 
	 */
	public ArrayList<String> getLines(String name) {
		Book book = getBook(name);
		ArrayList<String> lines = book.lines();
		return lines;
	}
	/**
	 * Returns the entire Library of books for the Model. 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @return HashMap representation of all Books in the library. 
	 */
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
	 * @param fileName, represents the file name of the book. 
	 * @return returns true if book is contained in library and false if not. 
	 */
	public boolean contains(String fileName) {
		if (bookLibrary.containsKey(fileName)) {
			return true;
		}
		return false;
	}
}

