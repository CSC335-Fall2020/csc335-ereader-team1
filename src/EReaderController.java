import java.util.ArrayList;
import java.util.HashMap;

public class EReaderController {
	
	
	EReaderModel model = null;
	
	/**
	 * this is thre constructor 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @param model
	 */
	public EReaderController(EReaderModel model) {
		this.model = model;
	}
	
	/**
	 * get library
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @return the library
	 */
	public HashMap<String, Book> getLibrary() {
		return model.getLibrary();
	}
	
	/**
	 * to get the book by its name
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @param the book with its name 
	 * @return
	 */
	public Book getBook(String name) {
		return model.getBook(name);
	}
	
	/**
	 * get the lines 
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @param the lines 
	 */
	public ArrayList<String> getLines(String name) {
		return model.getLines(name);
	}
	
	/**
	 * This method is used by the controller to interact with the model and 
	 * open a file in the library/model and set the file as book in the library.
	 * @author Ali Hamza 
	 * @author Sultan Alnhari
	 * @param bookName
	 */
	public void openFile(String bookName) {
		model.readBook(bookName);
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the next page.
	 * 
	 * @author jackguerin
	 */
	public void getNext() {
		model.getNextPage();
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the previous page.
	 * 
	 * @author jackguerin
	 */
	public void getPrev() {
		model.getPrevPage();
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the first page.
	 * 
	 * @author jackguerin
	 * @param fileName is a string representing the book to open
	 */
	public void openBook(String fileName) {
		model.openBook(fileName);
	}
	
	public double getProgress() {
		return model.getProgress();
	}
	
	/**
	 * @author Sultan Alnhari 
	 * check if the book found or not 
	 * @return return true if the book is spelled wrong and not valid 
	 */
	public boolean bookNotFoundss() {
		return model.bookNotFounds();
	}
	
	/**
	 * Checks if a book is contained in the library model.
	 * 
	 * @author jackguerin
	 * @param String fileName represents the file name of the book
	 * @return returns true if book is contained in library and false if not
	 */
	public boolean containsBook(String fileName) {
		return model.contains(fileName);
	}
}
