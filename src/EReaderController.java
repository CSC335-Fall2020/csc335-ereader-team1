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
	 */
	public void openBook() {
		model.openBook();
	}
	
	public void openBook(String fileName) {
		model.openBook(fileName);
	}
	
}
