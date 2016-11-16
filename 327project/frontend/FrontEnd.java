import java.util.*;
import java.io.*;

/**
 * The FrontEnd class, contains methods for checking valid account numbers,
 * handling transaction input, and outputing to the transaction summary file.
 * Has ArrayList validAccounts to store all valid account numbers to cross check
 * with user input account numbers, and ArrayList tempTransSummary to store all
 * of the summary codes of successful transactions in order to print out to
 * transaction summary file at the end of a session. Is the main class in the
 * project and makes use agent and atm class instances.
 *
 * @author 13ttm
 */
public class FrontEnd {

	private int[] validAccounts;
	private ArrayList<String> tempTransSummary = new ArrayList<String>();

	/**
	 * Main method responsible for the continouse program loop and the session
	 * loop that takes user input, handles the input, and prints out the
	 * transaction summary file. Takes arguments array containing the file names
	 * for the valid accounts file to read from and the transaction summary file
	 * to print to.
	 *
	 * @arg args[] arguments array containing the input and output files
	 *      required of the program
	 */
	public static void main(String args[]) throws IOException {


		// Creates a new front end object allowing program access to methods and
		// data that have been privated
		FrontEnd frontEnd = new FrontEnd();

		// Creates a new bufferedReader that reads from the standard input
		// stream
		BufferedReader transactions = new BufferedReader(new InputStreamReader(System.in));

		String transaction = "";

		// Continuous loop to keep program constantly running
		while (true) {
			try {
				// Gets the next transaction from the input stream
				transaction = transactions.readLine();
				if (transaction == null) {
					/**
					 * Only time transaction can be null is if we are reading
					 * the end of a file. Therefore we are testing and want to
					 * exit the continuous loop because we are done the current
					 * test case and must continue witht he rest of the test
					 * cases
					 **/
					System.exit(0);
				}
			} catch (IOException e) {

			}
			// Checks to see if first transaction is a login
			if (transaction.equals("login")) {
				// Uses a general object user so that we can instantiate either an agent or atm
				Session user;
				int type;
				System.out.println("Please enter the type of session (atm/agent)");
				if (transactions.readLine().equals("atm")) {
					type = 0;
					System.out.println("Logging in as atm");
				} else {
					type = 1;
					System.out.println("Logging in as agent");
				}
				try {
					// Try to read the valid accounts file
					frontEnd.readAccounts(new BufferedReader(new FileReader(new File(args[0]))));

					user = new Session(type,frontEnd.validAccounts);

					// begin session loop
					do {
					
						// Gets the next transaction from the input stream
						transaction = transactions.readLine();
						if (transaction == null) {
							/**
							 * Only time transaction can be null is if we are
							 * reading the end of a file. Therefore we are
							 * testing and want to exit the continuous loop
							 * because we are done the current test case and
							 * must continue with he rest of the test cases
							 **/
							System.exit(0);
						}

						// string to hold the resulting summary code from the
						// command
						String result = null;
						// switch statement will choose which command to run

						switch (transaction) {
						case "create":
							result = user.create(transactions);
							break;
						case "delete":
							result = user.delete(transactions);
							break;

						case "deposit":
							result = user.deposit(transactions);
							break;
						case "withdraw":
							result = user.withdraw(transactions);
							break;

						case "transfer":
							result = user.transfer(transactions);
							break;

						case "logout":
							// deletes current atm/agent session
							user = null;
							// sets result to end of summary code
							result = ("ES 00000000 00000000 000 ***");

							System.out.println("Logging out");
							break;

						default:
							// none of the the transactions specified in the
							// problem set where used.
							// print an error
							System.out.println("Error: Invalid transaction");
							break;
						}
						// If result is null this means a transaction has failed
						// and thus there is no transaction
						// summary code to store in the arraylist for
						// transaction summary codes to be printed
						if (result != null) {
							frontEnd.tempTransSummary.add(result);
						}
					// exit out of session loop when the transaction applied is
					// logout
					} while (!transaction.equals("logout"));
				} catch (IOException e) {
					// If the file was unable to be read, output an error
					e.printStackTrace();
				}
				// end of session, print the transaction summary file
				frontEnd.printSummaryFile(args[1]);

			} else {
				// Tried to do a transaction before login. Print an error
				// message.
				System.out.println("Error: not logged in");
			}
		}

	}

	/**
	 * Prints all transactions made in session to the transaction summary file.
	 * The file to print into is specified by the String argument.
	 *
	 * @param file
	 *            A String representing the name of the file to print the
	 *            transaction summary into
	 */
	private void printSummaryFile(String file) {
		try {
			// Creates a new file writer, setting it up to write to the file
			// passed into the method
			FileWriter fs = new FileWriter(file);
			// for every succesful transaction this session (except for the
			// logout transaction)
			for (int i = 0; i < this.tempTransSummary.size() - 1; i++) {
				// write the summary code to the file
				fs.write(this.tempTransSummary.get(i) + "\n");
			}
			// writes the end of session code to the file
			fs.write(this.tempTransSummary.get(this.tempTransSummary.size() - 1));
			// clears all the transaction codes stored in the ArrayList
			this.tempTransSummary.clear();
			// closes the file writer stream
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads all account numbers from the valid accounts file and stores them in
	 * an {@link ArrayList}. The method takes a {@link BufferedReader} as its
	 * only parameter to use for reading line by line from the valid accounts
	 * file.
	 *
	 * @param reader 	The BufferedReader setup to read from the valid accounts file
	 */
	private void readAccounts(BufferedReader reader) throws IOException {
		ArrayList<String> temp = new ArrayList<String>();
		// reads the first line of the accounts file
		String str = reader.readLine();
		// while we have not reached the end of the accounts file
		while (!str.equals("00000000")) {
			// add the account read into the valid accounts ArrayList
			temp.add(str);
			// read the next line of the file
			str = reader.readLine();
		}// done reading the file
		validAccounts = new int[temp.size()];
		for(int i = 0 ; i < temp.size();i++){
			validAccounts[i] = Integer.parseInt(temp.get(i));
		}
	}

}// End of FrontEnd Class
