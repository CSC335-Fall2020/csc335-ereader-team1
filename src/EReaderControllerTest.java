import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class acts as a JUnit test class the tests every method/line in the
 * controller, model, and book classes. Each test goes through certain situations
 * that might be reached when any of the classes are changed or updated.
 * 
 * @author jackguerin
 */
public class EReaderControllerTest {
	
	/**
	 * This tests the controllers method that opens a file that
	 * does exist.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestOpenFile() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
	}
	
	/**
	 * This tests the controllers method that grabs the HashMap that
	 * represents the models library of books.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestGetLibrary() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
		HashMap<String, Book> library = controller.getLibrary();
		Assertions.assertEquals(library.size(), 1);
	}
	
	/**
	 * This tests the controllers method that grabs a certain book
	 * from the model.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestGetBook() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
		HashMap<String, Book> library = controller.getLibrary();
		Book book = controller.getBook("book1.txt");
		Assertions.assertEquals(library.get("book1.txt"), book);
	}
	
	/**
	 * This tests the controllers method that checks if a file is not
	 * found of the given name to be opened.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestBookNotFound() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book.txt");
		Assertions.assertTrue(controller.bookNotFoundss());
	}
	
	/**
	 * This tests the controllers method that gets the ArrayList of lines
	 * from the current book in the model.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestGetLines() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openBook("book1.txt");
		controller.getLines("book1.txt");
	}
	
	/**
	 * This tests the controllers get next page method.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestGetNext() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openBook("book1.txt");
		controller.getNext();
		controller.getNext();
		controller.getNext();
	}
	
	/**
	 * This tests the controllers get previous page method.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestGetPrev() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openBook("book1.txt");
		controller.getNext();
		controller.getPrev();
	}
	
	/**
	 * This tests the method in the controller class that checks if
	 * a book is contained in the model. It specefically checks if
	 * a book is contained in the model.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestContainsBook() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
		Assertions.assertTrue(controller.containsBook("book1.txt"));
	}
	
	/**
	 * This tests the method in the controller class that checks if
	 * a book is contained in the model. It specefically checks if
	 * a book is not in the model.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestDoesNotContainBook() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book2.txt");
		Assertions.assertFalse(controller.containsBook("book1.txt"));
	}
	
	/**
	 * This tests if the method that grabs a current books
	 * progress works.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestProgress() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openBook("book1.txt");
		double progress = controller.getProgress();
		Assertions.assertTrue(progress > 0.0);
	}
	
	/**
	 * This tests if the method in the book class that grabs
	 * the current page works.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestBookCurPage() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openBook("book1.txt");
		Book book = controller.getBook("book1.txt");
		Assertions.assertEquals(book.getCurPage(), 1);
	}
	
	/**
	 * This tests if a book being added to the model already
	 * exists in its HashMap.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestAlreadyExistingBook() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
		controller.openBook("book1.txt");
		controller.openBook("book1.txt");
	}
	
	/**
	 * This tests the next page method in order to check the bounds of the
	 * book in order to not go beyond the books size.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestEndOfBookBounds() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openBook("book2.txt");
		controller.getNext();
	}
	
	/**
	 * This tests the previous page method in order to check the bounds of the
	 * book in order to not go beyond the books size.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestBeginningOfBookBounds() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openBook("book1.txt");
		controller.getPrev();
	}
	
	/**
	 * This tests the situation where an imported book has a smaller
	 * size of words than that will appear on a page, so the openBook 
	 * method will change the number of words on a page.
	 * 
	 * @author Jack Guerin
	 */
	@Test
	public void TestChangeOfWordsOnPage() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openBook("book3.txt");
	}
}
