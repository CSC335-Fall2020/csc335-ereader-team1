
import java.util.ArrayList;
import java.util.Arrays;

public class EReaderMain {
	public static void main(String[] args) {
		EReaderModel model = new EReaderModel();
		EReaderController controller = new EReaderController(model);
		ArrayList<String[]> array = controller.getLines("book1.txt");
		for(int i = 0;i<array.size();i++) { 
			System.out.println(Arrays.deepToString(array.get(i)));
		}
	}
}
