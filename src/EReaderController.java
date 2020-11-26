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
	public ArrayList<String[]> getLines(String name) {
		return model.getLines(name);
	}
	
}
