import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Library.java
 * 
 * @author Alexandru Mihalache, Vlad Stejeroiu
 * @author Victor Ciobanu, Andrei Predi
 * @version 1.0 - no copyright.
 */
public class Library {

	ArrayList<Integer> books = new ArrayList<Integer>();
	static int[] scores;
	int signUp;
	int nrShips;
	int ID;
	long score;

	/**
	 * Comparator to perform a descending order 
	 * of books by their scores.
	 */
	class SortbyBookScore implements Comparator<Integer> {
		public int compare(Integer a, Integer b) {
			return World.books[b] - World.books[a];
		}
	}

	/**
	 * Creates a Library.
	 * 
	 * @param books   indices of the book which are in the library.
	 * @param scores  each score of each book.
	 * @param signUp  how long the sign up process it is.
	 * @param nrShips how many books the library can ship/day.
	 * @param ID      the library ID.
	 */
	public Library(ArrayList<Integer> books, int[] scores, int signUp, int nrShips, int ID) {
		this.books = books;
		Library.scores = scores;
		this.signUp = signUp;
		this.nrShips = nrShips;
		this.ID = ID;
		Collections.sort(books, new SortbyBookScore());
	}

}
