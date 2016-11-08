import java.util.*;
import java.io.*;

public class Session {
	private int type;
	private int maxTransAmount;
	private int[] withdrawn;
	private int[] accounts;
	private String invalidAccounts;

	public Session(int type, int[] accounts) {
		this.invalidAccounts = "";
		this.type = type;
		this.accounts = accounts;
		this.withdrawn = new int[accounts.length];
		if (type == 0) {
			this.maxTransAmount = 100000;
		} else if (type == 1) {
			this.maxTransAmount = 99999999;
		}
	}

	public String deposit(BufferedReader br) throws IOException {
		int acc = this.getInput(br, "Enter the account number");
		if (this.validAccount(acc, true)) {
			int amnt = this.getInput(br, "Enter the amount to deposit");
			if (this.validAmount(amnt)) {
				System.out.println("Depositing " + amnt + " cents into account " + acc);
				return "DE " + acc + " 00000000 " + amnt + " ***";
			} else {
				System.out.println("Error: amount deposited must be between 0 and " + this.maxTransAmount);
			}
		} else {
			System.out.println("Error: Invalid account number");
		}
		return null;
	}

	public String withdraw(BufferedReader br) throws IOException {
		int acc = this.getInput(br, "Enter the account number");
		if (this.validAccount(acc, true)) {
			int amnt = this.getInput(br, "Enter the amount to withdraw");
			if (this.validAmount(amnt)) {
				int accIndex = this.getAccountIndex(acc);
				if (amnt + this.withdrawn[accIndex] <= this.maxTransAmount) {
					System.out.println("Withdrawing " + amnt + " cents from account " + acc);
					this.withdrawn[accIndex] += amnt;
					return "WD " + "00000000 " + acc + " " + amnt + " ***";
				}
				System.out.println("Withdraw limit has been reached on account");

			} else {
				System.out.println("Error: amount withdrawed must be between 0 and " + this.maxTransAmount);
			}
		} else {
			System.out.println("Error: Invalid account number");
		}
		return null;
	}

	public String transfer(BufferedReader br) throws IOException {
		int acc = this.getInput(br, "Enter the account number of the transfer source");
		if (this.validAccount(acc, true)) {
			int acc2 = this.getInput(br, "Enter the account number of the transfer destination");
			if (this.validAccount(acc2, true)) {
				int amnt = this.getInput(br, "Enter the amount to transfer");
				if (this.validAmount(amnt)) {
					int accIndex = this.getAccountIndex(acc);
					if (amnt + this.withdrawn[accIndex] <= this.maxTransAmount) {
						System.out
								.println("Transfering " + amnt + " cents from account " + acc + " to account " + acc2);
						this.withdrawn[accIndex] += amnt;
						return "TR " + acc + " " + acc2 + " " + amnt + " ***";
					}
					System.out.println("Withdraw limit has been reached on account");

				} else {
					System.out.println("Error: amount transfered must be between 0 and " + this.maxTransAmount);
				}
			} else {
				System.out.println("Error: Invalid account number");
			}
		} else {
			System.out.println("Error: Invalid account number");
		}
		return null;
	}

	public String create(BufferedReader br) throws IOException {
		if (this.type > 0) {
			int acc = this.getInput(br, "Enter the account number of the account to create");
			if (this.validAccount(acc, false)) {
				String name = this.getStrInput(br, "Enter the account name");
				if (validName(name)) {
					System.out.println("Creating account " + acc);
					this.invalidAccounts = this.invalidAccounts + ";" + acc;
					return "CR " + acc + " 00000000 000 " + name;
				} else {
					System.out.println("Error: account name must be between 3 and 30 alphanumberic characters");
				}

			} else {
				System.out.println("Error: Invalid account number");
			}
		} else {
			System.out.println("Error: Insufficient Permissions");
		}
		return null;
	}

	public String delete(BufferedReader br) throws IOException {
		if (this.type > 0) {
			int acc = this.getInput(br, "Enter the account number of the account to delete");
			if (this.validAccount(acc, true)) {
				String name = this.getStrInput(br, "Enter the account name");
				if (validName(name)) {
					System.out.println("Deleting account " + acc);
					this.accounts[this.getAccountIndex(acc)] = -1;
					this.invalidAccounts = this.invalidAccounts + ";" + acc;
					return "DL " + acc + " 00000000 000 " + name;
				} else {
					System.out.println("Error: account name must be between 3 and 30 alphanumberic characters");
				}

			} else {
				System.out.println("Error: Invalid account number");
			}
		} else {
			System.out.println("Error: Insufficient Permissions");
		}
		return null;
	}

	private String getStrInput(BufferedReader br, String query) throws IOException {
		System.out.println(query);
		return br.readLine();
	}

	private int getInput(BufferedReader br, String query) throws IOException {
		try {
			return Integer.parseInt(this.getStrInput(br, query));
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	private boolean validAccount(int accountNum, boolean shouldExist) {

		if(Integer.toString(accountNum).length() == 8){
			// Checks if the account is in the accounts array
			for (int i = 0; i < this.accounts.length; i++) {
				if (this.accounts[i] == accountNum) {
					if(shouldExist){
						return true;
					}else{
						return false;
					}
				}
			}
			if(!this.invalidAccounts.contains(Integer.toString(accountNum))){
				return !shouldExist;
			}
		}
		
		return false;
	}

	private boolean validAmount(int amnt) {
		return (amnt > 0 && amnt <= maxTransAmount);
	}

	private boolean validName(String name) {
		int size = name.length();
		return (size <= 30 && size >= 3) && !(name.startsWith(" ") || name.endsWith(" "));
	}

	private int getAccountIndex(int acc) {
		for(int i = 0; i < this.accounts.length;i++){
			if(this.accounts[i] == acc){
				return i;
			}
		}
		return -1;
		//return this.withdrawn[Arrays.binarySearch(this.accounts, acc)];
	}
}