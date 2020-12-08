import java.util.ArrayList;
import java.util.HashMap;

public class EReaderController {
	
	
	EReaderModel model = null;
	
	/**
	 * 
	 * @param model
	 */
	public EReaderController(EReaderModel model) {
		this.model = model;
	}
	
	/**
	 * 
	 * @return
	 */
	public HashMap<String, Book> getLibrary() {
		return model.getLibrary();
	}
	
	public Book getBook(String name) {
		return model.getBook(name);
	}
	
	/**
	 * 
	 * @param name
	 */
	public ArrayList<String> getLines(String name) {
		return model.getLines(name);
	}
	
	/**
	 * This method is used by the controller to interact with the model and 
	 * open a file in the library/model and set the file as book in the library.
	 * 
	 * @param bookName
	 */
	public void openFile(String bookName) {
		model.readBook(bookName);
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the next page.
	 */
	public void getNext() {
		model.getNextPage();
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the previous page.
	 */
	public void getPrev() {
		model.getPrevPage();
	}
	
	/**
	 * This method is used by the controller to interact with the library/model and
	 * the library's current displayed book to turn the current displayed book's
	 * to the first page.
	 * 
	 * @param fileName is a string representing the book to open
	 */
	public void openBook(String fileName) {
		model.openBook(fileName);
	}
	
	public double getProgress() {
		return model.getProgress();
	}
	
	/**
	 * check if the book found or not 
	 * @return return true if the book is spelled wrong and not valid 
	 */
	public boolean bookNotFoundss() {//////////////////
		return model.bookNotFounds();
	}
	
	/**
	 * Checks if a book is contained in the library model.
	 * 
	 * @param String fileName represents the file name of the book
	 * @return returns true if book is contained in library and false if not
	 */
	public boolean containsBook(String fileName) {
		return model.contains(fileName);
	}
}
