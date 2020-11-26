import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EReaderModel {
	public EReaderModel() {
		try {
			File myObj = new File("book1.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}

	}
	
}
