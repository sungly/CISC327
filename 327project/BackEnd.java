import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/*
*The BackEnd class reads the previous day master accounts file and applies all the transactions made in the merged summary file(set
*of daily transaction files). After all transacntions have been read, BackEnd writes a new master accounts file as well as a new
*valid account file. 
*Date: November 14th
*Authors: Cloud Programming
*/
public class BackEnd {
	static ArrayList<String[]> accounts = new ArrayList<String[]>();
	
	/*
	*Main Method is responsible for reading the original master accounts file and 
	*storing it in a ArrayList<String[]> accounts. It then iterates through all the 
	*transactions in the merged summary file and applies all the changes to the ArrayList.
	*Once all the transactions have been read, main writes new master accounts file and
	*new valid account file.
	*
	*/
	public static void main(String[] args) {
		BufferedReader br;
		//stores master accounts file into an ArrayList
		try {
			br = new BufferedReader(new FileReader("MasterAccounts.txt"));
			String[] line = input(br);
			while (line != null) {
				accounts.add(line);
				line = input(br);
			}
			br.close();
			
			//Reads each line from the merged summary file
			br = new BufferedReader(new FileReader("MergedTransactions.txt"));
			line = input(br);
			while (!line[0].equals("ES")) {
				switch (line[0]) {
				case "DE":
					addInt(line[1], Integer.parseInt(line[3]));
					break;
				case "WD":
					addInt(line[2], -1 * Integer.parseInt(line[3]));
					break;
				case "TR":
					addInt(line[1], Integer.parseInt(line[3]));
					addInt(line[2], -1 * Integer.parseInt(line[3]));
					break;
				case "CR":
					createAccount(line[1], line[4]);
					break;
				case "DL":
					deleteAccount(line[1], line[4]);
					break;
				default:
					break;
				}
			}
			br.close();
			
			//writes a new master accounts file and a new valid account file for the next session to use
			writeFile("MasterAccounts.txt", false);
			writeFile("../frontend/validaccounts.txt", true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}//end main
	
	/*
	* Deposit, withdraw and transfer all share this method to perform its transaction. Checks that the account number
	* is valid, the current amount is not negative and that the String length of the account amount is greater than 3. 
	* 
	* @params accountNum	The account number
	*	  amount	The account amount
	*/
	public static void addInt(String accountNum, int amount) {
		int index = getAccount(accountNum);
		if (index != -1) {
			int current = Integer.parseInt(accounts.get(index)[1]);
			current += amount;
			if (current < 0) {
				System.out.println("Failed constraint: account balance is negative");
			} else {
				String temp = Integer.toString(current);
				while (temp.length() < 3) {
					temp = "0".concat(temp);
				}
				accounts.get(index)[1] = temp;
			}
		}
	}//end addInt
	
	/**
	* createAccount adds a new account to the ArrayList accounts if the account doesn't already exist.
	*
	* @params accountNum	The account number from the transaction summary
	*	  amount	The account amount from the transaction summary
	*/
	public static void createAccount(String accountNum, String accountName) {
		if (getAccount(accountNum) != -1) {
			System.out.println("Failed constraint: account number already exists");
		} else {
			int index = 0;
			while (index < accounts.size() && Integer.parseInt(accountNum) > Integer.parseInt(accounts.get(index)[0])) {
				index++;
			}
			accounts.add(index, new String[] { accountNum, "000", accountName });
		}
	}//end createAccount
	
	/**
	* deleteAccount deletes an account from the ArrayList accounts if the account exists, and the account amount is exactly "000"
	*
	* @params accountNum	The account number from the transaction summary
	*	  amount	The account amount from the transaction summary
	*/
	public static void deleteAccount(String accountNum, String accountName) {
		int index = getAccount(accountNum);
		if (index == -1) {
			System.out.println("Failed constraint: account number doesn't exist");
		} else {
			if (!accounts.get(index)[1].equals("000")) {
				System.out.println("Failed constraint: account doesn't have zero balance");
			} else {
				if (!accounts.get(index)[2].equals(accountName)) {
					System.out.println("Failed constraint: account name doesn't match");
				} else {
					accounts.remove(index);
				}
			}
		}//end outer else

	}//end deleteAccounts
	
	/*
	* getAccount iterates through the the ArrayList accounts to check if a given account number exists or not. 
	* Returns the index within the ArrayList accounts if found, otherwise returns -1.
	*
	* @params accountNum	The account number from the transaction summary
	*/
	public static int getAccount(String accountNum) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).equals(accountNum)) {
				return i;
			}
		}
		return -1;
	}//end getAccount
	
	/**
	* Takes the name of a file to write to and boolean partial to determine if its the master accounts file
	* or the valid account file. If True, writeFile writes the validaccount.txt and only takes the account numbers
	* from the ArrayList accounts. Otherwise, it takes each value in the ArrayList accounts and writes a new 
	*MasterAccounts.txt file.
	*
	*@params file		Name of the file to write to
		 partial 	True for validaccount, False for MasterAccounts
	*/
	public static void writeFile(String file, boolean partial) throws IOException {
		FileWriter fw = new FileWriter(file);
		for (int i = 0; i < accounts.size(); i++) {
			if (partial) {
				fw.write(accounts.get(i)[0]);
			} else {
				fw.write(Arrays.toString(accounts.get(i)));
			}
		}//end for
		fw.flush();
		fw.close();
	}//end writeFile
	
	/**
	*Reads a line from a given file. Returns null if the line is null. Each return String
	*is turned into an array using split(" ").
	*
	*@param br 	The BufferedReader setup to read from a given file. 	*
	*/
	public static String[] input(BufferedReader br) throws IOException {
		String line = br.readLine();
		if (line == null) {
			return null;
		}
		return line.split(" ");
	}//end input
}//end BackEnd
