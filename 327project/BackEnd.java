import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BackEnd {
	static ArrayList<String[]> accounts = new ArrayList<String[]>();

	public static void main(String[] args) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("MasterAccounts.txt"));
			String[] line = input(br);
			while (line != null) {
				accounts.add(line);
				line = input(br);
			}
			br.close();

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

			writeFile("MasterAccounts.txt", false);
			writeFile("../frontend/validaccounts.txt", true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
	}

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
	}

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
		}

	}

	public static int getAccount(String accountNum) {
		for (int i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).equals(accountNum)) {
				return i;
			}
		}
		return -1;
	}

	public static void writeFile(String file, boolean partial) throws IOException {
		FileWriter fw = new FileWriter(file);
		for (int i = 0; i < accounts.size(); i++) {
			if (partial) {
				fw.write(accounts.get(i)[0]);
			} else {
				fw.write(Arrays.toString(accounts.get(i)));
			}
		}
		fw.flush();
		fw.close();
	}

	public static String[] input(BufferedReader br) throws IOException {
		String line = br.readLine();
		if (line == null) {
			return null;
		}
		return line.split(" ");
	}
}
