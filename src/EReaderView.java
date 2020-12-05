import java.util.Observable;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EReaderView extends Application implements java.util.Observer {
	
	private EReaderController controller = new EReaderController(new EReaderModel());
	
	/* Saves current page number */
	private int curPage = 0;
	private String fileName;
	
	/* Saves border pane for update use */
	private BorderPane border;
	
	/* Font settings */
	private String fontType = "Times New Roman";
	private int fontSize = 12;
	
	private double progress = 0;
	
	private Menu library;
	

	@Override
	public void update(Observable o, Object arg) {
		/*
		 * The view is observing whichever book that the library/model has
		 * designated as the current book to look at
		 */
		Book curBook = (Book) o;
		
		/* This string represents the words on the current page to display */
		String words = (String) arg;
		
		/*
		 * Creates a stack pane to stack the label representing the page on
		 * top of the gray background
		 */
		StackPane stackCenter = new StackPane();
		
		Rectangle pageRect = new Rectangle(800, 700, Color.WHITE);
		
		/* Below is the code to create a label to display as the current page */
		TextArea page = new TextArea(words);/////////////label///////////////
		/* Wraps the text to window's size */
		page.setWrapText(true);
		/* Changes the wrapping nature of text to only the center of BorderPane */
		page.setPadding(new Insets(50)); 
		page.setEditable(false);  ////////////////new//////////////	
		
		stackCenter.getChildren().addAll(pageRect, page);
		
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
		/* Sets and aligns the page counter to the bottom center of the BorderPane */
		border.setBottom(pageNum);
		BorderPane.setAlignment(pageNum, Pos.TOP_CENTER);
		
		////////////////////////progress bar//////////////////////////
		ProgressBar progressBar = new ProgressBar();
		progress = 0.1;
		progressBar.setProgress(controller.getProgress());
		
		TilePane tileButtoms = new TilePane(Orientation.HORIZONTAL);
		tileButtoms.setPadding(new Insets(20, 50, 20, 150));
		tileButtoms.setHgap(150.0);
		tileButtoms.setVgap(8.0);
		tileButtoms.getChildren().addAll(progressBar, pageNum);
		border.setBottom(tileButtoms);
		//////////////////////////////////////////////////
		
	}
	
	private class Open extends Stage {
			
		private String fileName;
			
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

				controller.openFile(fileName);
				
				if (controller.bookNotFoundss() == true) { //////// to check if the book is right
					erorr(null);
					this.close();
					return;
				}
				
				this.fileName = fileName;
				controller.getBook(fileName).addObserver(EReaderView.this);
				controller.openBook(fileName);
				
				MenuItem newBook = new MenuItem(fileName);
				library.getItems().add(newBook);
				
				newBook.setOnAction(libraryEvent -> {
					controller.openBook(fileName);
				});
				
				this.close();
			});
			
			grid.add(open, 0, 1);
			
			Scene scene = new Scene(grid);
			this.setScene(scene);
			
			initModality(Modality.APPLICATION_MODAL);
		}
			
	}

	@Override
	public void start(Stage stage) throws Exception {
		/* 
		 * Below is a commented line to add the view as an observer of the model
		 */
		
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
		
		///////////////////////////////////////////
		/* Below is the button that will save */
		Button savetButton = new Button("Save");
		savetButton.setOnAction(event -> {
			
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
		settings.getItems().add(fontCustomMenuItem);
		
		/* Below adds the option to type in a new font size */
		Text fontSizeText = new Text("Font Size");
		TextField fontSizeField = new TextField(Integer.toString(this.fontSize));
		fontSizeField.setPrefWidth(35);
		
		HBox fontSizeHBox = new HBox(10, fontSizeText, fontSizeField);
		fontSizeHBox.setAlignment(Pos.BASELINE_CENTER);
		
		CustomMenuItem fontSizeBox = new CustomMenuItem(fontSizeHBox);
		settings.getItems().add(fontSizeBox);
		
		/* Below is the button that will apply these new changes */
		Button apply = new Button("Apply");
		apply.setOnAction(event -> {
			this.fontSize = Integer.parseInt(fontSizeField.getText());
			this.fontType = fontField.getText();
		});
		
		CustomMenuItem applyButton = new CustomMenuItem(apply);
		settings.getItems().add(applyButton);
		
		Menu library = new Menu("Library");
		this.library = library;
		
		/* Lastly this adds the menus to the menu bar */
		menuBar.getMenus().add(file);
		menuBar.getMenus().add(settings);
		menuBar.getMenus().add(library);
		
		/////////////////////////////////////////////////////////////////////////////////
		// adding the find field under this are
		/////////////////////////////////////////////////////////////////////////////////
		/* This menu option will allow a user to find the word */
		Menu find = new Menu("Find");
		
		Text find_here = new Text("Find");
		TextField findField = new TextField();
		
		HBox findHBox = new HBox(10, find_here, findField);
		fontHBox.setAlignment(Pos.BASELINE_CENTER);
		
		CustomMenuItem findCustomMenuItem = new CustomMenuItem(findHBox);
		find.getItems().add(findCustomMenuItem);
		menuBar.getMenus().add(find);
		
		/* Below is the button that will apply these new changes */
		Button findButton = new Button("Find");
		findButton.setOnAction(event -> {
			
		});
		
		CustomMenuItem findButtonCustomMenuItem = new CustomMenuItem(findButton);
		find.getItems().add(findButtonCustomMenuItem);
		
		
		/* Below is the button that will apply these new changes */
		Button highlightButton = new Button("Highlight");
		highlightButton.setOnAction(event -> {
			
		});
		
		CustomMenuItem highlightButtonCustomMenuItem = new CustomMenuItem(highlightButton);
		settings.getItems().add(highlightButtonCustomMenuItem);
		
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
			
			controller.getPrev();
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
			
			controller.getNext();
			
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
		
		
		Rectangle pageRect = new Rectangle(800, 700, Color.WHITE);
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
	 * this method will control the error message if book is not found or spilled wrong
	 * @param ActionEvent e action event
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