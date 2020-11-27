import java.util.ArrayList;
import java.util.Arrays;

public class EReaderMain {
	public static void main(String[] args) {
		EReaderModel model = new EReaderModel();
		EReaderController controller = new EReaderController(model);
		ArrayList<String> line = controller.getLines("book1.txt");
//		System.out.println(line);
		for (int i = 0; i < line.size(); i++) {
			String word = line.get(i);
			if(word.equals("")) {
					System.out.println();
			}
			else {
				System.out.print(word + " ");				
			}
		}

	}
}
