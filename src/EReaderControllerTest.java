import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EReaderControllerTest {
	
	@Test
	public void TestOpenFile() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
	}
	
	@Test
	public void TestGetLibrary() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
		HashMap<String, Book> library = controller.getLibrary();
		Assertions.assertEquals(library.size(), 1);
	}
	
	@Test
	public void TestGetBook() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
		HashMap<String, Book> library = controller.getLibrary();
		Book book = controller.getBook("book1.txt");
		Assertions.assertEquals(library.get("book1.txt"), book);
	}
	
	@Test
	public void TestBookNotFound() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book.txt");
		Assertions.assertTrue(controller.bookNotFoundss());
	}
	
	@Test
	public void TestGetLines() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book.txt");
		controller.openBook("book1.txt");
		controller.getLines("book1.txt");
	}
	
	@Test
	public void TestGetNext() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book.txt");
		controller.openBook("book1.txt");
		controller.getNext();
	}
	
	@Test
	public void TestGetPrev() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book.txt");
		controller.openBook("book1.txt");
		controller.getPrev();
	}
	
	@Test
	public void TestContainsBook() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book1.txt");
		Assertions.assertTrue(controller.containsBook("book1.txt"));
	}
	
	@Test
	public void TestProgress() {
		EReaderController controller = new EReaderController(new EReaderModel());
		controller.openFile("book.txt");
		controller.openBook("book1.txt");
		double progress = controller.getProgress();
		Assertions.assertTrue(progress > 0.0);
	}
}
