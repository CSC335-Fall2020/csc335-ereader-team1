import java.util.Arrays;

public class EReaderMain {
	public static void main(String[] args) {
		EReaderModel model = new EReaderModel();
		String[] array = model.getLines("book1.txt").get(1);
		System.out.print(Arrays.deepToString(array));
	}
}
