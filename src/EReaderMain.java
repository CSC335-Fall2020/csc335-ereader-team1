import java.util.Arrays;

public class EReaderMain {
	public static void main(String[] args) {
		EReaderModel model = new EReaderModel();
		EReaderController controller = new EReaderController(model);
		String[] array = controller.getLines("book1.txt").get(0);
		System.out.print(Arrays.deepToString(array));
	}
}
