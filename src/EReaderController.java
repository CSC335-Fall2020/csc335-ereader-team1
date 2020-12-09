import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller interface for EReader application. Interacts with Model. 
 * @author Ali Hamza
 * @author Sultan Alnhari
 * @author jackguerin
 */
public class EReaderController {
	
	EReaderModel model = null;
	
	/**
	 * Constructor method. 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @param model, Model object passed in by GUI. 
	 */
	public EReaderController(EReaderModel model) {
		this.model = model;
	}
	
	/**
	 * Library getter method. 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @return HashMap representation of the working library. 
	 */
	public HashMap<String, Book> getLibrary() {
		return model.getLibrary();
	}
	
	/**
	 * Gets a book by its name. 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @param name, String name of the book to find. 
	 * @return Book representation of book found.  
	 */
	public Book getBook(String name) {
		return model.getBook(name);
	}
	
	/**
	 * Gets ArrayList representation of all words in Book. 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @param name, String name of book to retrieve from. 
	 * @return ArrayList of all words in book. 
	 */
	public ArrayList<String> getLines(String name) {
		return model.getLines(name);
	}
	
	/**
	 * This method is used by the controller to interact with the model and 
	 * open a file in the library/model and set the file as book in the library.
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @param bookName, String representation of a new book to add to library. 
	 */
	public void openFile(String bookName) {
		model.readBook(bookName);
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the next page.
	 * 
	 * @author Jack Guerin
	 */
	public void getNext() {
		model.getNextPage();
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the previous page.
	 * 
	 * @author Jack Guerin
	 */
	public void getPrev() {
		model.getPrevPage();
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the first page.
	 * 
	 * @author Jack Guerin
	 * @param fileName is a string representing the book to open.
	 */
	public void openBook(String fileName) {
		model.openBook(fileName);
	}
	
	/**
	 * Returns the overall progress of the current book's page versus its overall contents.
	 * 
	 * @author Jack Guerin
	 * @return double between 0.0-1.0 to represent overall progress. 
	 */
	public double getProgress() {
		return model.getProgress();
	}
	
	/**
	 * @author Sultan Alnhari 
	 * Checks if the book has been found or not. 
	 * @return return true if the book is spelled wrong and not valid. 
	 */
	public boolean bookNotFoundss() {
		return model.bookNotFounds();
	}
	
	/**
	 * Checks if a book is contained in the library model.
	 * 
	 * @author Jack Guerin
	 * @param String fileName represents the file name of the book.
	 * @return returns true if book is contained in library and false if not.
	 */
	public boolean containsBook(String fileName) {
		return model.contains(fileName);
	}
}
