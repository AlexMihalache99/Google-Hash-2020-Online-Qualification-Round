import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * OptimizedWorld.java
 * 
 * @author Alexandru Mihalache
 * @version 2.0 - no copyright.
 *
 */
public class OptimizedWorld {

	/**
	 * Comparator to perform an anscending order of libraries by the sign up process
	 * and descending order by the number of books they can ship/day. This sort gets
	 * the best scores on a), b), c).
	 *
	 */
	class SortbySignUP implements Comparator<Library> {

		public int compare(Library a, Library b) {
			if (a.signUp - b.signUp > 0) {
				return 1;
			} else if (a.signUp - b.signUp < 0) {
				return -1;
			} else {
				return b.nrShips - a.nrShips;
			}

		}
	}

	/**
	 * Comparator to perform a descending order of libraries by the sign up process
	 * and descending order by their score.
	 *
	 */
	class SortbySignUP1 implements Comparator<Library> {

		public int compare(Library a, Library b) {
			if (a.signUp - b.signUp > 0) {
				return 1;
			} else if (a.signUp - b.signUp < 0) {
				return -1;
			} else {
				return (int) (Library.getScore(b) - Library.getScore(a));
			}

		}
	}

	/**
	 * Comparator to perform an ascending order of libraries by how many books they
	 * can ship/day and ascending order by how long the sign up process is. This
	 * helps to get the best score on d).
	 */
	class SortbyShips implements Comparator<Library> {

		public int compare(Library a, Library b) {
			if (a.nrShips - b.nrShips > 0) {
				return 1;
			} else if (a.nrShips - b.nrShips < 0) {
				return -1;
			} else {
				return a.signUp - b.signUp;
			}

		}
	}

	/**
	 * Sorting the libraries by their score. This helps to get the best score on d).
	 *
	 */
	class SortbyScore implements Comparator<Library> {
		public int compare(Library a, Library b) {
			return Library.getScore(b) - Library.getScore(a);
		}
	}

	/**
	 * Comparator to perform a descending order of libraries by the score they have
	 * divided by the signUp time. This helps to get the best score on f).
	 */
	class SortbyTotalScoreandSignUP implements Comparator<Library> {
		public int compare(Library a, Library b) {
			return Library.getScore(b) / b.signUp - Library.getScore(a) / a.signUp;
		}
	}

	/**
	 * Sorting the libraries by their possible score. This helps to get the best
	 * score on e).
	 *
	 */
	class SortbyScore1 implements Comparator<Library> {
		public int compare(Library a, Library b) {
			return Library.getScore1(a) - Library.getScore1(b);
		}
	}

	static int days;
	int nrBooks;
	int nrLibraries;
	static int[] books = new int[100000];
	ArrayList<Library> libraries = new ArrayList<Library>();
	ArrayList<Library> sol = new ArrayList<Library>();
	int size;

	/**
	 * Parsing all the information from the file to the attributes.
	 * 
	 * @param filename the file to be read.
	 */
	void parse(String filename) {

		int bufferSize = 8 * 1024;

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(filename), bufferSize);
			String line = bufferedReader.readLine();
			String[] l = line.split(" ");
			nrBooks = intValue(l[0]);
			nrLibraries = intValue(l[1]);
			days = intValue(l[2]);

			String line1 = bufferedReader.readLine();
			String[] l1 = line1.split(" ");

			for (int i = 0; i < nrBooks; i++) {
				books[i] = intValue(l1[i]);
			}

			for (int i = 0; i < nrLibraries; i++) {
				String line2 = bufferedReader.readLine();
				String[] l2 = line2.split(" ");
				int nrB = intValue(l2[0]);
				int signUp = intValue(l2[1]);
				int nrShips = intValue(l2[2]);
				ArrayList<Integer> booksIndices = new ArrayList<Integer>();

				String line3 = bufferedReader.readLine();
				String[] l3 = line3.split(" ");

				for (int j = 0; j < nrB; j++) {

					booksIndices.add(intValue(l3[j]));
				}

				Library lib = new Library(booksIndices, books, signUp, nrShips, i);
				libraries.add(lib);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void simulate() {

		/**
		 * This gets the best score on d).
		 */

		/*
		 * Collections.sort(libraries, new SortbyShips());
		 * 
		 * for (int i = 0; i < libraries.size(); i++) { sol.add(libraries.get(0));
		 * 
		 * for (int j = 1; j < libraries.size(); j++) { removeBooks(libraries.get(0),
		 * libraries.get(j)); } libraries.remove(0); Collections.sort(libraries, new
		 * SortbyScore()); } sol = libraries;
		 */

		/**
		 * This gets the best score on e).
		 */
		int daysLeft = days;
		while (daysLeft > 0 && libraries.isEmpty() == false) {
			Collections.sort(libraries, new SortbyScore1());
			Library l = libraries.get(libraries.size() - 1);

			if (Library.getScore1(l) == 0) {
				break;
			}

			daysLeft -= l.signUp;
			for (int i = 0; i < libraries.size() - 1; i++) {
				removeBooks(l, libraries.get(i));
			}
			sol.add(l);
			libraries.remove(l);

		}

		/**
		 * This algorithm gets the best score on f).
		 */
		/*
		 * Collections.sort(libraries, new SortbyTotalScoreandSignUP());
		 * 
		 * int totalSignUP = 0; int index = 0; while (index < libraries.size()) {
		 * 
		 * if (totalSignUP + libraries.get(index).signUp > days) { break; }
		 * 
		 * totalSignUP += libraries.get(index).signUp; sol.add(libraries.get(index));
		 * index++;
		 * 
		 * }
		 * 
		 * int progress = 0; size = 0;
		 * 
		 * for (int i = 0; i < sol.size(); i++) {
		 * 
		 * progress += sol.get(i).signUp;
		 * 
		 * if (days - progress > 0 && sol.get(i).nrShips > 0) { int bookSendCapacity =
		 * (days - progress) * sol.get(i).nrShips; size++; for (int j = 0; j <
		 * sol.get(i).books.size() && j <= bookSendCapacity; j++) {
		 * sol.get(i).chosenBooks.add(sol.get(i).books.get(j)); } } }
		 */

	}

	/**
	 * Printing the output to the file.
	 * 
	 * @param filename the file to be write on.
	 */
	void print(String filename) {

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");

			writer.println(sol.size());

			/**
			 * This is used for a), b), c), d), e).
			 */
			for (Library library : sol) {
				if (library.books.size() != 0) {
					writer.println(library.ID + " " + library.books.size());
					for (int book : library.books) {
						writer.print(book + " ");
					}
					writer.println();
				}
			}

			/**
			 * This is used just for the best case on f).
			 */

			/*
			 * writer.println(size); for (Library lib : sol) {
			 * 
			 * if (lib.chosenBooks.size() == 0) { continue; }
			 * 
			 * writer.print(lib.ID + " " + lib.chosenBooks.size() + "\n"); for (Integer book
			 * : lib.chosenBooks) { writer.print(book + " "); } writer.println(); }
			 */

			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Converting a string to an Int.
	 * 
	 * @param s string to be converted.
	 * @return the int value of the string.
	 */
	public static int intValue(String s) {
		return Integer.parseInt(s);
	}

	/**
	 * Removing books which were already used from a library.
	 * 
	 * @param a the library which was scanned.
	 * @param b the library to remove books from.
	 */
	public void removeBooks(Library a, Library b) {

		for (Integer i : a.books) {
			b.books.remove(i);
		}
	}

}
