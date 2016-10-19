import java.util.*;
import java.io.*;
public class FrontEnd{
	
	private ArrayList<String> validAccounts = new ArrayList<String>();
	private ArrayList<String> tempTransSummary = new ArrayList<String>();
	
	public static void main(String args[]) throws IOException{

		//Creates a new front end object allowing program access to methods and data that have been privated
		FrontEnd frontEnd = new FrontEnd();
		
		//Creates a new bufferedReader that reads from the standard input stream
		BufferedReader transactions = new BufferedReader(new InputStreamReader(System.in));

		try{
			//Try to read the valid accounts file
			frontEnd.readAccounts(new BufferedReader(new FileReader(new File(args[0]))));
		}catch(IOException e){
			System.out.println("Error: File Input for valid accounts");
		}
		
		String transaction;
		
		//Continuous loop to keep program constantly running
		while(true){
			try{
				transaction = transactions.readLine();
				if(transaction == null){
					//Only time transaction can be null is if we are reading the end of a file. Therefore we are testing and want
					//to exit the continuous loop because we are done.
					break;
				}
			}catch(IOException e){
				transaction = "";
			}
			if(transaction.equals("login")){
				Object user;
				System.out.println("Please enter the type of session (atm/agent)");
				if(transactions.readLine().equals("atm")){
					user = new Atm();
				}else{
					user = new Agent();
				}
				do{
					try{
						transaction = transactions.readLine();
						if (transaction == null){
							break;	
						}

						String[] params = new String[3];
						String result = null;
						switch(transaction){
							case "create":	
											if(user instanceof Agent){
												System.out.println("Enter the account number of the new account");
												params[0] = transactions.readLine();
												if(frontEnd.validAccount(params[0]) && !frontEnd.validAccounts.contains(params[0])){
													System.out.println("Enter the account name");
													params[1] = transactions.readLine();
													result = ((Agent)user).create(params);
												}else{
													System.out.println("Error: Invalid account number");
												}
											}else{
												System.out.println("Error: Insufficient Permissions");
											}
											break;
							case "delete":	
											if(user instanceof Agent){
												System.out.println("Enter the account number of the account to delete");
												params[0] = transactions.readLine();
												if(frontEnd.validAccount(params[0]) && frontEnd.validAccounts.contains(params[0])){
													System.out.println("Enter the account name");
													params[1] = transactions.readLine();
													result = ((Agent)user).delete(params);
													frontEnd.validAccounts.remove(params[0]);
												}else{
													System.out.println("Error: Invalid account number");
												}
											}else{
												System.out.println("Error: Insufficient Permissions");
											}
											break;
							case "deposit": 
											System.out.println("Enter the account number to deposit to");
											params[0] = transactions.readLine();
											if(frontEnd.validAccount(params[0]) && frontEnd.validAccounts.contains(params[0])){
												System.out.println("Enter the amount to deposit");
												params[1] = transactions.readLine();
												if(user instanceof Atm){
													result =((Atm)user).deposit(params);
												}else{
													result =((Agent)user).deposit(params);
												}
											}
											break;
							case "withdraw": 
											System.out.println("Enter the account number to withdraw from");
											params[0] = transactions.readLine();
											if(frontEnd.validAccount(params[0]) && frontEnd.validAccounts.contains(params[0])){
												System.out.println("Enter the amount to withdraw");
												params[1] = transactions.readLine();
												if(user instanceof Atm){
													result =((Atm)user).withdraw(params);
												}else{
													result =((Agent)user).withdraw(params);
												}
											}
											break;
							case "transfer":
											System.out.println("Enter the account number of the transfer source");
											params[0] = transactions.readLine();
											if(frontEnd.validAccount(params[0]) && frontEnd.validAccounts.contains(params[0])){
												System.out.println("Enter the account number of the transfer destination");
												params[1] = transactions.readLine();
												if(frontEnd.validAccount(params[1]) && frontEnd.validAccounts.contains(params[1])){
													System.out.println("Enter the amount to transfer");
													params[2] = transactions.readLine();
													if(user instanceof Atm){
														result =((Atm)user).transfer(params);
													}else{
														result =((Agent)user).transfer(params);
													}
												}
											}
											break;
							case "logout":	user = null;
											result =("ES");
											break;
							default:
									System.out.println("Invalid transaction");
							
						}
						if (result != null){
							frontEnd.tempTransSummary.add(result);
						}
					}catch(IOException e){
						e.printStackTrace();
					} 
				}while(!transaction.equals("logout"));
				
				frontEnd.printSummaryFile(args[1]);

			}
		}
		
		
		
	
	}
	

	private boolean validAccount(String accountNum){
		return (accountNum.length() == 8 && accountNum.charAt(0) != '0');
	}

	
	private void printSummaryFile(String file){
		try{
			FileWriter fs = new FileWriter(file);
			for(int i = 0; i < this.tempTransSummary.size()-1; i++){
				fs.write(this.tempTransSummary.get(i) + "\n");
			}
			fs.write(this.tempTransSummary.get(this.tempTransSummary.size()-1));
			this.tempTransSummary.clear();
			fs.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void readAccounts(BufferedReader reader){
		try{
			String str = reader.readLine();
			while(!str.equals("00000000")){
				this.validAccounts.add(str);
				str = reader.readLine();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
