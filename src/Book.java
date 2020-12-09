import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class Book extends Observable {

	private ArrayList<String> words = new ArrayList<>();
	
	/* Represents current page in the book */
	private int curPage = 0;
	
	/* Field that holds the book marked page in the book */
	private int bookmark = 0;
	
	/* Current Index of first word of current page */
	private int curIndex = 0;
	
	/* Field that represents how many words are on a page */
	
	private int wordsOnPage = 150;
	
	private boolean bookNotFound;////////////////
	
	private float progress = 0;

	/**
	 * constructor of the book 
	 * @author Ali Hamza @author Sultan Alnhari 
	 * @param name
	 */
	public Book(String name) {
		readBook(name);
	}

	/**
	 * this method will read the book 
	 * @author Ali Hamza @author Sultan Alnhari 
	 * @param name
	 */
	public void readBook(String name) {
		try {
			File myObj = new File(name);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] line = data.replaceAll("[\\\r\\\n]", "").trim().split("\\s+");
				for(int i = 0;i<line.length;i++) {
					words.add(line[i]);	
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			this.bookNotFound = true;/////////////////////
			System.out.println("Error");
		}
	}
	
	/** @author Sultan Alnhari 
	 * check if the book found or not 
	 * @return return true if the book is spelled wrong and not valid 
	 */
	public boolean bookNotFound() {
		return this.bookNotFound;
	}

	public ArrayList<String> lines() {
		return words;
	}
	
	/**
	 * This method opens the current book to the first page in the
	 * book.
	 * 
	 * @author jackguerin
	 */
	public void openBook() {
		
		/* This string holds the words for the first page of the book */
		String firstPage = "";
		float incriment = ((float) wordsOnPage) / words.size();
		
		/*
		 * Checks the size of the book in order to change the amount of
		 * words that will appear on the first page.
		 */
		if (wordsOnPage > words.size()) {
			wordsOnPage = words.size()-1;
		}
		
		/* This index represents the next pages first words index */
		int wordIndex = wordsOnPage + 1;
		
		/*
		 * This for loop iterates over the first page of words in the book
		 * until it reaches the index of the first word on the next page
		 */
		for (int i = bookmark; i < wordIndex + bookmark; i++) {
			
			/* Adds to current word to the first page string */
			firstPage += words.get(i) + " ";
		}
		/* Displays that the current first page is 1 */
		this.curPage = 1;
		this.curIndex = 0;
		progress = incriment;
		
		/* Updates the view accordingly */
		setChanged();
		notifyObservers(firstPage);
	}
	
	/**
	 * This method turns finds and grabs the next page of words
	 * in the current displayed book and updates the view
	 * with those string of words.
	 * 
	 * @author jackguerin
	 */
	public void nextPage() {
		
		/* This string represents the previous page of words */
		String nextPage = "";
		float incriment = ((float) wordsOnPage) / words.size();
		
		/* This index represents the next pages first words index */
		int wordIndex = wordsOnPage + 1;
			
		/*
		 * The page will not turn if the it is the last page in the
		 * book, but it will still update the view. If the cur page
		 * is not the last page, then the curIndex integer will
		 * add to the index of the first word on the next
		 * page
		 */
		if (curIndex + wordIndex < words.size()) {
			curIndex += wordIndex;
			this.curPage += 1;
		}
		
		/*
		 * This for loop iterates over the words starting at the index
		 * of the first word on the next page and iterates until
		 * it has reached whatever number of words is wordsOnPage.
		 */
		for (int i = curIndex; i <= wordsOnPage + curIndex; i++) {
			
			/* Only happens if it is the end of the book */
			if (i == words.size()) {
				break;
			}
			/* Adds the next word to the next page string */
			nextPage += words.get(i) + " ";
		}
		
		if (progress < 1) {
			progress += incriment;
		}
			
		/* Updates the view accordingly */
		setChanged();
		notifyObservers(nextPage);
	}
	
	/**
	 * This method finds and grabs the previous page of words
	 * in the current displayed book and updates the view
	 * with those string of words.
	 * 
	 * @author jackguerin
	 */
	public void prevPage() {
		
		/* This string represents the previous page of words */
		String prevPage = "";
		float incriment = ((float) wordsOnPage) / words.size();
		
		/* This index represents the previous pages first words index */
		int wordIndex = wordsOnPage + 1;
		
		/*
		 * The page will not turn if the it is the first page in the
		 * book, but it will still update the view. If the cur page
		 * is not the first page, then the curIndex integer will
		 * subtract to the index of the first word on the previous
		 * page
		 */
		if (curIndex - wordIndex >= 0) {
			curIndex -= wordIndex;
			this.curPage -= 1;
		}
		
		/*
		 * This for loop iterates over the words starting at the index
		 * of the first word on the previous page and iterates until
		 * it has reached whatever number of words is wordsOnPage.
		 */
		for (int i = curIndex; i <= wordsOnPage + curIndex; i++) {
			
			/* Adds the next word to the previous page string */		
			prevPage += words.get(i) + " ";
		}
		
		if (progress > incriment) {
			progress -= incriment;
		}
		
		/* Updates the view accordingly */
		setChanged();
		notifyObservers(prevPage);
	}
	
	/**
	 * This method returns the current page number of this
	 * book.
	 * 
	 * @author jackguerin
	 * @return integer representing the current page number
	 */
	public int getCurPage() {
		return curPage;
	}
	
	/**
	 * This method returns the progress for the current book
	 * instance.
	 * 
	 * @author jackguerin
	 * @return float representing the current progress
	 */
	public float getProgress() {
		return this.progress;
	}
}