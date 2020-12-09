import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * GUI view for E-Reader. 
 * @author Jack Guerin
 * @author Anthony
 * @author Sultan Alnhari
 */
public class EReaderView extends Application implements java.util.Observer {
	
	private EReaderController controller = new EReaderController(new EReaderModel());
	
	/* Saves current page number */
	private int curPage = 0;
	private String fileName = null;
	
	/* Saves border pane for update use */
	private BorderPane border;
	
	/* Font settings */
	private String fontType;
	private int fontSize;
	
	// Maps to hold data for settings and library/bookmarks.
	private HashMap<String, String> settingMap;
	private HashMap<String, Integer> bookList;
	
	// Saves a persistent copy of library tab so multiple classes and methods can work with it. 
	private Menu library;
	
	// Dark mode selection indicator
	private int mode;
	
	/**
	 * This method updates the current view with a page of words from an imported
	 * book. This method will update the page of words, the page number, the 
	 * progress bar, the font size, the font type, and even which mode the view
	 * is set to (dark or light).
	 * 
	 * Updates the GUI every time the previous page or the next page is accessed, or a new book is
	 * opened. 
	 * 
	 * @author Jack Guerin
	 * @author Sultan Alnhari
	 * @author Anthony
	 * @param o is the Observable instance that is being updated.
	 * @param arg is an object that will always be an ArrayList of words that represent a page.
	 */
	@Override
	public void update(Observable o, Object arg) {
		/*
		 * The view is observing whichever book that the library/model has
		 * designated as the current book to look at
		 */
		Book curBook = (Book) o;
		curPage = curBook.getCurPage();
		
		/* This string represents the words on the current page to display */
		String words = (String) arg;
		
		/*
		 * Creates a stack pane to stack the label representing the page on
		 * top of the gray background
		 */
		StackPane stackCenter = new StackPane();
		
		/* Below is the code to create a label to display as the current page */
		TextArea page = new TextArea(words);/////////////label///////////////
		
		/*
		 * Sets the colors to be appropriate for dark/light mode using CSS style settings. 
		 */
		if(mode == 0) {
			border.setBackground(new Background(new BackgroundFill(Color.gray(0.3), null, null)));
			page.setStyle("-fx-text-fill: white; -fx-background-color: rgb(64, 64, 64); "
					+ "-fx-control-inner-background: rgb(64, 64, 64);");
		} else {
			border.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
			page.setStyle("-fx-text-fill: black; -fx-background-color: white; -fx-control-inner-background: white;");
		}
		
		/* Wraps the text to window's size */
		page.setWrapText(true);
		/* Changes the wrapping nature of text to only the center of BorderPane */
		page.setPadding(new Insets(50)); 
		page.setEditable(false);  ////////////////new//////////////	
		
		stackCenter.getChildren().addAll(page);
		
		StackPane.setAlignment(page, Pos.TOP_CENTER);
		
		/*
		 * Below is the code to display the font for the current page; however, this
		 * code is likely to change with the implementation of the settings
		 */
		Font font = new Font(fontType, fontSize + 16);
		page.setFont(font);
		
		/* Sets the page to the center of the BorderPane */
		border.setCenter(stackCenter);
		/* Aligns the page starting at the top left of the center */
		BorderPane.setAlignment(stackCenter, Pos.TOP_CENTER);
		
		/* Below is the code to display the current page number */
		Text pageNum = new Text("Page " + Integer.toString(curBook.getCurPage()));
		pageNum.setFont(Font.font(20));
		if(mode == 0) { //Dark mode font style setting.
			pageNum.setFill(Color.WHITE);
		} else {
			pageNum.setFill(Color.BLACK);
		}
		/* Sets and aligns the page counter to the bottom center of the BorderPane */
		border.setBottom(pageNum);
		BorderPane.setAlignment(pageNum, Pos.TOP_CENTER);
		
		/////////////// progress bar @author Sultan Alnhari//
		ProgressBar progressBar = new ProgressBar();
		progressBar.setProgress(controller.getProgress());
		
		TilePane tileButtoms = new TilePane(Orientation.HORIZONTAL);
		tileButtoms.setPadding(new Insets(20, 50, 20, 150));
		tileButtoms.setHgap(150.0);
		tileButtoms.setVgap(8.0);
		tileButtoms.getChildren().addAll(progressBar, pageNum);
		border.setBottom(tileButtoms);
		//////////////////////////////////////////////////
		
	}
	
	/**
	 * This private class which extends stage acts as a window that allows
	 * for a user to open/import a file as book to the library and open
	 * it to the first page on the view.
	 * 
	 * Opens a new Book within the controller and displays it on the GUI. 
	 * 
	 * @author Jack Guerin
	 * @author Anthony
	 */
	private class Open extends Stage {
		
		/**
		 * Opens a book either by importing it or accessing it through the existing library. 
		 * 
		 * @author Jack Guerin
		 */
		public Open() {
			this.setTitle("Import File");
			
			/* Creates grid pane for the import file window */
			GridPane grid = new GridPane();
			grid.setHgap(8);
			grid.setVgap(8);
			grid.setPadding(new Insets(8));
			
			/* Below adds the text box for writing a file name */
			Text name = new Text("File Name");
			TextField fileField = new TextField();
			HBox fileBox = new HBox(10, name, fileField);
			fileBox.setAlignment(Pos.BASELINE_CENTER);
			
			grid.add(fileBox, 0, 0);
			
			/* Below creates a button that reads what the text box said */
			Button open = new Button("Open");
			
			/*
			 * This action opens reads the textfield and opens the file
			 * by using a controller method to open the file within the model
			 * I think the name for the method in the controller to open a file
			 * should be called "openFile"
			 */
			open.setOnAction(event -> {
				fileName = fileField.getText();
				/* Call method from controller to open file */
				
				// Makes sure that the book does not already exist in the library
				boolean check = controller.containsBook(fileName);

				controller.openFile(fileName);
				
				if (controller.bookNotFoundss() == true) { // Sultan, to check if the book is right
					erorr(null);
					this.close();
					return;
				}
				
				controller.getBook(fileName).addObserver(EReaderView.this);
				curPage = controller.getBook(fileName).getCurPage();
				controller.openBook(fileName);
				
				/* Does not add new menu item if book is already in library, acts on boolean discovered earlier */
				if (check) {
					this.close();
					return;
				}
				
				MenuItem newBook = new MenuItem(fileName);
				library.getItems().add(newBook);
				bookList.put(fileName, 0);
				writeLibrary();
				
				newBook.setOnAction(libraryEvent -> {
					controller.openBook(newBook.getText());
					if(bookList.get(fileName) != 0) {
						for(int i = 1; i < bookList.get(fileName); i++) {
							controller.getNext();
						}
					}
				});
				
				this.close();
			});
			
			grid.add(open, 0, 1);
			
			Scene scene = new Scene(grid);
			this.setScene(scene);
			
			initModality(Modality.APPLICATION_MODAL);
		}
		
		/**
		 * Modified version of Open() constructor, operating on the idea that the book already
		 * exists as a MenuItem and simply needs to be displayed from the library. 
		 * @author Anthony
		 * @param bookName, name of the book to display from the library. 
		 */
		public Open(String bookName) {
			controller.openFile(bookName);
			if (controller.bookNotFoundss() == true) { // @author Sultan Alnhari  to check if the book is right
				erorr(null);
				this.close();
				return;
			}
			fileName = bookName;
			controller.getBook(bookName).addObserver(EReaderView.this);
			curPage = controller.getBook(bookName).getCurPage();
			controller.openBook(bookName);
			if(bookList.get(bookName) != 0) {
				for(int i = 1; i < bookList.get(bookName); i++) {
					controller.getNext();
				}
			}
			this.close();
		}
			
	}

	@Override
	/**
	 * This method starts the view from main and opens up the window with
	 * a menu bar with menus to allow a user to update the view by adding a book,
	 * changing the color, changing the settings, or updating the page. The view
	 * also displays progress and page numbers at the bottom of the view.
	 * 
	 * Starts the landing page of the E-Reader GUI program. 
	 * 
	 * @author Jack Guerin 
	 * @param stage, the operating JavaFX stage. 
	 * @throws Exception
	 */
	public void start(Stage stage) throws Exception {
		/* 
		 * Below is a commented line to add the view as an observer of the model
		 * add some touches to the start - Sultan Alnhari 
		 */
		
		loadSettings();
		
		stage.setTitle("E-Reader");
		
		MenuBar menuBar = new MenuBar();
		
		/* This is the menu option that will open a file */
		Menu file = new Menu("File");
		MenuItem importFile = new MenuItem("Import...");
		file.getItems().add(importFile);
		
		/*
		 * This action will open a separate window to allow 
		 * for a user to open a file 
		 */
		importFile.setOnAction(even -> {
			Open openFile = new Open();
			openFile.showAndWait();
		});
		
		// @author Sultan Alnhari, button structure and lambda wrapper
		/* Below is the button that will bookmark the current page. */
		Button savetButton = new Button("Save");
		savetButton.setOnAction(event -> {
			// @author Anthony, operative code to set a bookmark
			if(fileName != null) { //Check so that there are no complications if a book hasn't been loaded.
				bookList.put(fileName, curPage);
				writeLibrary();
			}
		});
		
		CustomMenuItem saveButtonCustomMenuItem = new CustomMenuItem(savetButton);
		file.getItems().add(saveButtonCustomMenuItem);
		///////////////////////////////////////////
		
		/* This menu option will allow a user to change the font and font size */
		Menu settings = new Menu("Settings");
		
		/* Below adds the option to type in a new font type */
		Text font = new Text("Font");
		TextField fontField = new TextField(fontType);
		
		HBox fontHBox = new HBox(10, font, fontField);
		fontHBox.setAlignment(Pos.BASELINE_CENTER);
		
		CustomMenuItem fontCustomMenuItem = new CustomMenuItem(fontHBox);
		fontCustomMenuItem.setHideOnClick(false);
		settings.getItems().add(fontCustomMenuItem);
		
		/* Below adds the option to type in a new font size */
		Text fontSizeText = new Text("Font Size");
		TextField fontSizeField = new TextField(Integer.toString(this.fontSize));
		fontSizeField.setPrefWidth(35);
		
		HBox fontSizeHBox = new HBox(10, fontSizeText, fontSizeField);
		fontSizeHBox.setAlignment(Pos.BASELINE_CENTER);
		
		CustomMenuItem fontSizeBox = new CustomMenuItem(fontSizeHBox);
		fontSizeBox.setHideOnClick(false);
		settings.getItems().add(fontSizeBox);
		
		Text modeText = new Text("Dark Mode");
		CheckBox modeField = new CheckBox();
		modeField.setSelected(mode==0);
		HBox modeHBox = new HBox(10, modeText, modeField);
		modeHBox.setAlignment(Pos.BASELINE_CENTER);
		
		CustomMenuItem modeBox = new CustomMenuItem(modeHBox);
		modeBox.setHideOnClick(false);
		settings.getItems().add(modeBox);
		
		/* Below is the button that will apply these new changes */
		Button apply = new Button("Apply");
		Text applyText = new Text("Will be updated when page changes");
		apply.setOnAction(event -> {
			fontSize = Integer.parseInt(fontSizeField.getText());
			settingMap.put("fontSize", String.valueOf(fontSize));
			fontType = fontField.getText();
			settingMap.put("fontType", fontType);
			if(modeField.isSelected()) {
				settingMap.put("displayMode", "0");
				mode = 0;
			} else {
				settingMap.put("displayMode", "1");
				mode = 1;
			}
			writeSettings();
		});
		HBox applyHBox = new HBox(apply, applyText);
		CustomMenuItem applyButton = new CustomMenuItem(applyHBox);
		settings.getItems().add(applyButton);
		
		Menu library = new Menu("Library");
		this.library = library;
		
		loadLibrary();
		
		/* Lastly this adds the menus to the menu bar */
		menuBar.getMenus().add(file);
		menuBar.getMenus().add(settings);
		menuBar.getMenus().add(library);
		
		/////////////////////////////////////////////////////////////////////////////////
		//  @author Sultan Alnhari adding the find field under this are
		/////////////////////////////////////////////////////////////////////////////////
		/* This menu option will allow a user to find the word */
		
//		Menu find = new Menu("Find");
//		
//		Text find_here = new Text("Find");
//		TextField findField = new TextField();
//		
//		HBox findHBox = new HBox(10, find_here, findField);
//		fontHBox.setAlignment(Pos.BASELINE_CENTER);
//		
//		CustomMenuItem findCustomMenuItem = new CustomMenuItem(findHBox);
//		find.getItems().add(findCustomMenuItem);
//		menuBar.getMenus().add(find);
//		
//		/* Below is the button that will apply these new changes */
//		Button findButton = new Button("Find");
//		findButton.setOnAction(event -> {
//			
//		});
//		
//		CustomMenuItem findButtonCustomMenuItem = new CustomMenuItem(findButton);
//		find.getItems().add(findButtonCustomMenuItem);
//		
//		
//		/* Below is the button that will apply these new changes */
//		Button highlightButton = new Button("Highlight");
//		highlightButton.setOnAction(event -> {
//			
//		});
//		
//		CustomMenuItem highlightButtonCustomMenuItem = new CustomMenuItem(highlightButton);
//		settings.getItems().add(highlightButtonCustomMenuItem);
		
		/////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////

		
		/* This sets up the border pane below the menu bar */
		BorderPane border = new BorderPane();
		
		/* Below the previous page button is created */
		Button left = new Button("<");
		
		/*
		 * This action is meant to change the page to the
		 * previous page.
		 */
		left.setOnAction(event -> {
			/* Show previous page */
			if(curPage != 0) {
				controller.getPrev();
			}
		});
		
		left.setPadding(new Insets(15));
		border.setLeft(left);
		BorderPane.setAlignment(left, Pos.CENTER_LEFT);
		
		/* Below the next page button is created */
		Button right = new Button(">");
		
		/*
		 * This action is meant to change the page to the
		 * next page.
		 */
		right.setOnAction(event -> {
			/* Show next page */
			if(curPage != 0) {
				controller.getNext();
			}
		});
		
		right.setPadding(new Insets(15));
		border.setRight(right);
		BorderPane.setAlignment(right, Pos.CENTER_RIGHT);
		
		/* Sets the menuBar to the top of the border pane */
		border.setTop(menuBar);
		
		/* 
		 * Below sets up the current page number, feel free
		 * to change up the way its shown
		 */
		Text pageNum = new Text("Page " + Integer.toString(curPage));
		pageNum.setFont(Font.font(20));
		border.setBottom(pageNum);
		BorderPane.setAlignment(pageNum, Pos.BOTTOM_CENTER);
		Rectangle pageRect = null;
		
		/*
		 * This section of the code changes the initial design based on whether or not Dark Mode is
		 * selected. 
		 */
		if(mode == 0) {
			border.setBackground(new Background(new BackgroundFill(Color.gray(0.3), null, null)));
			pageRect = new Rectangle(800, 700, Color.rgb(64, 64, 64));
			pageNum.setFill(Color.WHITE);
		} else {
			border.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
			pageRect = new Rectangle(800, 700, Color.WHITE);
			pageNum.setFill(Color.BLACK);
		}
		
		border.setCenter(pageRect);
		BorderPane.setAlignment(pageRect, Pos.CENTER);
		
		/* 
		 * Stores the border pane as a private field to be used
		 * in the update
		 */
		this.border = border;

		Scene scene = new Scene(border, 920, 1080);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Loads the settings on startup from a file, Settings.txt, recording font style, font size,
	 * and a Dark Mode toggle. If the Settings.txt file does not exist, it creates a new one.
	 * @author Anthony
	 * @throws Exception
	 */
	private void loadSettings() throws Exception {
		this.settingMap = new HashMap<String, String>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("Settings.txt"));
			String line = bufferedReader.readLine();
			while(line != null) {
				String[] setting = line.split(",");
				settingMap.put(setting[0], setting[1]);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (FileNotFoundException f) {
			try {
				File newSetting = new File("Settings.txt");
				newSetting.createNewFile();
			} catch (IOException g){
				g.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Loads defaults if the settings did not exist in Settings.txt.
		if(settingMap.containsKey("fontType")) {
			this.fontType = settingMap.get("fontType");
		} else {
			this.fontType = "Times New Roman";
			settingMap.put("fontType", "Times New Roman");
		}
		if(settingMap.containsKey("fontSize")) {
			this.fontSize = Integer.valueOf(settingMap.get("fontSize"));
		} else {
			this.fontSize = 12;
			settingMap.put("fontSize", "12");
		}
		if(settingMap.containsKey("displayMode")) {
			this.mode = Integer.valueOf(settingMap.get("displayMode"));
		} else {
			this.mode = 1;
			settingMap.put("displayMode", "1");
		}
		writeSettings();
	}
	
	/**
	 * Writes the user's view settings to a file. This method is called when first launched, in
	 * case the file did not exist/had insufficient information for next time, or whenever
	 * the settings are updated by the user. 
	 * @author Anthony
	 */
	private void writeSettings() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Settings.txt"));
			String newSettings = "";
			for(String x : settingMap.keySet()) {
				newSettings += x + "," + settingMap.get(x) + "\n";
			}
			bufferedWriter.write(newSettings);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the library from a file, recording filenames/paths and bookmarked page, if exists.
	 * If the library file, Library.txt, does not exist, it creates a new Library.txt file. 
	 * This method is called at launch, a.k.a. start().
	 * @author Anthony
	 * @throws Exception
	 */
	private void loadLibrary() throws Exception {
		this.bookList = new HashMap<String, Integer>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("Library.txt"));
			String line = bufferedReader.readLine();
			while(line != null) {
				String[] book = line.split("s3p4r4t0r");
				//Note: "s3p4r4t0r" is used as it is unlikely to exist within the path string.
				bookList.put(book[0], Integer.valueOf(book[1]));
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		} catch (FileNotFoundException f) {
			try {
				File newLibrary = new File("Library.txt");
				newLibrary.createNewFile();
			} catch (IOException g){
				g.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String bookName : bookList.keySet()) {
			MenuItem newBook = new MenuItem(bookName);
			library.getItems().add(newBook);
			newBook.setOnAction(libraryEvent -> {
				@SuppressWarnings("unused")
				Open openBook = new Open(newBook.getText());
			});
			
		}
	}
	
	/**
	 * Method to write contents of bookList (basically a HashMap of filenames/paths and current pages)
	 * to a text file, Library.txt. This method is called every time a new file is imported or a bookmark
	 * is saved. 
	 * @author Anthony
	 */
	private void writeLibrary() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Library.txt"));
			String newLib = "";
			for(String x : bookList.keySet()) {
				newLib += x + "s3p4r4t0r" + bookList.get(x) + "\n";
				//Note: "s3p4r4t0r" is used as it is unlikely to exist within the path string.
			}
			bufferedWriter.write(newLib);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will control the error message if book is not found or spelled wrong. 
	 * @author Sultan Alnhari
	 * @param e, action event
	 */
	public void erorr(ActionEvent e) {
		Alert alert = new Alert(AlertType.NONE);
        // set alert type to be an error
		alert.setAlertType(AlertType.ERROR); 
		alert.setContentText("Book not found, check the spelling of the book!");
        // show and wait
		alert.showAndWait();    
	}

}