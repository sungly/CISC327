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
			//If the file was unable to be read, output an error
			System.out.println("Error: File Input for valid accounts");
		}
		
		String transaction = "";
		
		//Continuous loop to keep program constantly running
		while(true){
			try{
				//Gets the next transaction from the input stream
				transaction = transactions.readLine();
				if(transaction == null){
					/**Only time transaction can be null is if we are reading the end of a file. Therefore we are testing and want to exit the continuous loop because we are
					 done the current test case and must continue witht he rest of the test cases
					**/
					break;
				}
			}catch(IOException e){

			}
			//Checks to see if first transaction is a login
			if(transaction.equals("login")){
				//Uses a general object user so that we can instantiate either an agent or atm
				Object user;
				System.out.println("Please enter the type of session (atm/agent)");
				if(transactions.readLine().equals("atm")){
					user = new Atm();
					System.out.println("Logging in as atm");
				}else{
					user = new Agent();
					System.out.println("Logging in as agent");
				}
				//begin session loop
				do{
					try{
						//Gets the next transaction from the input stream
						transaction = transactions.readLine();
						if (transaction == null){
							/**Only time transaction can be null is if we are reading the end of a
							 file. Therefore we are testing and want to exit the continuous loop
							 because we are done the current test case and must continue witht he 
							 rest of the test cases**/
							break;	
						}

						//a string array used to hold the parameters passed in by the user for the command
						String[] params = new String[3];
						//string to hold the resulting summary code from the command
						String result = null;
						//switch statement will choose which command to run
						switch(transaction){
							case "create":	
								//If the user is an agent then we gather command input and run create command
								if(user instanceof Agent){
									System.out.println("Enter the account number of the new account");
									//acquires account number and stores it
									params[0] = transactions.readLine();
									//checks whether the account number is valid
									if(frontEnd.validAccount(params[0]) && !frontEnd.validAccounts.contains(params[0])){
										System.out.println("Enter the account name");
										//acquires account name and stores it
										params[1] = transactions.readLine();
										/** passes user input for command into the create function of
										 the agent object and stores the resulting summary code into result**/
										result = ((Agent)user).create(params);
									}else{
										//Was given an invalid account number, print an error and 
										//don't do the transaction.
										System.out.println("Error: Invalid account number");
									}
								}else{
									//Isn't an agent, print a permissions error and don't do the transaction
									System.out.println("Error: Insufficient Permissions");
								}
								break;

							case "delete":
								//If the user is an agent hen we gather command input and run delete command
								if(user instanceof Agent){
									System.out.println("Enter the account number of the account to delete");
									//aquires account number and stores it
									params[0] = transactions.readLine();
									//checks whether the account number is valid
									if(frontEnd.validAccount(params[0]) && frontEnd.validAccounts.contains(params[0])){
										System.out.println("Enter the account name");
										//acquires account name and stores it
										params[1] = transactions.readLine();
										/**passes user input for command in to the delete function of
										the agent object and stores the resulting summary code into result**/
										result = ((Agent)user).delete(params);
										//Removes account from array of valid accounts so no further transactions
										//will be applied to account number
										frontEnd.validAccounts.remove(params[0]);
									}else{
										//Was given an invalid account number, print an error and
										//don't do the transaction
										System.out.println("Error: Invalid account number");
									}
								}else{
									//Isn't an agent, print a permissions error and don't do the transaction
									System.out.println("Error: Insufficient Permissions");
								}
								break;

							case "deposit": 
								System.out.println("Enter the account number to deposit to");
								//aquires account number and stores it
								params[0] = transactions.readLine();
								//checks whether the account number is valid
								if(frontEnd.validAccount(params[0]) && frontEnd.validAccounts.contains(params[0])){
									System.out.println("Enter the amount to deposit");
									//acquires amount to deposit in cents and stores it
									params[1] = transactions.readLine();
									//handles what to cast the user object into. Either atm or agent object depending on what
									// instance it is
									if(user instanceof Atm){
										/**passes user input for command in to the deposit function of
										the atm instance and stores the resulting summary code into result**/
										result =((Atm)user).deposit(params);
									}else{
										/**passes user input for command in to the deposit function of
										the agent instance and stores the resulting summary code into result**/
										result =((Agent)user).deposit(params);
									}
								}else{
									//Was given an invalid account number, print an error 
									//and don't do the transaction
									System.out.println("Error: Invalid account number");
								}
								break;

							case "withdraw": 
								System.out.println("Enter the account number to withdraw from");
								//aquires account number and stores it
								params[0] = transactions.readLine();
								//checks whether the account number is valid
								if(frontEnd.validAccount(params[0]) && frontEnd.validAccounts.contains(params[0])){
									System.out.println("Enter the amount to withdraw");
									//aquires the amount to withdraw in cents and stores it
									params[1] = transactions.readLine();
									//handles what to cast the user object into. Either atm or agent object depending on what
									// instance it is
									if(user instanceof Atm){
										/**passes user input for command into the withdraw function of 
										the atm instance and stores the resulting summary code into result **/
										result =((Atm)user).withdraw(params);
									}else{
										/**passes user input for command in to the withdraw function of
										the agent instance and stores the resulting summary code into result**/
										result =((Agent)user).withdraw(params);
									}
								}else{
									//Was given an invalid account number, print an error
									//and don't do the transaction
									System.out.println("Error: Invalid account number");
								}
								break;

							case "transfer":
								System.out.println("Enter the account number of the transfer source");
								//aquires transfer source account number and stores it
								params[0] = transactions.readLine();
								//checks whether the account number is valid
								if(frontEnd.validAccount(params[0]) && frontEnd.validAccounts.contains(params[0])){
									System.out.println("Enter the account number of the transfer destination");
									//aquires transfer destination account number and stores it
									params[1] = transactions.readLine();
									//checks whether the account number is valid
									if(frontEnd.validAccount(params[1]) && frontEnd.validAccounts.contains(params[1])){
										System.out.println("Enter the amount to transfer");
										//aquires the amount to transfer in cents and stores it
										params[2] = transactions.readLine();
										//handles what to cast the user object into. Either atm or agent object depending on what
										//instance it is
										if(user instanceof Atm){
											/**passes user input for command into the transfer function of 
											the atm instance and stores the resulting summary code into result**/
											result =((Atm)user).transfer(params);
										}else{
											/**passes user input for command into the transfer function of
											the agent instance and stores the resulting summary code into result **/
											result =((Agent)user).transfer(params);
										}
									}else{
										//Was given invalid account number, print an error
										//and don't do the transaction
										System.out.println("Error: Invalid account number");
									}
								}else{
									//Was given an invalid account number, print an error
									//and don't do the transaction
									System.out.println("Error: Invalid account number");
								}
								break;

							case "logout":	
								//deletes current atm/agent session
								user = null;
								//sets result to end of summary code
								result =("ES 00000000 00000000 ***");
								System.out.println("Logging out");
								break;

							default:
								//none of the the transactions specified in the problem set where used.
								//print an error
								System.out.println("Error: Invalid transaction");
								break;
						}
						//If result is null this means a transaction has failed and thus there is no transaction
						//summary code to store in the arraylist for transaction summary codes to be printed
						if (result != null){
							frontEnd.tempTransSummary.add(result);
						}
					}catch(IOException e){
						e.printStackTrace();
					} 
				//exit out of session loop when the transaction applied is logout
				}while(!transaction.equals("logout"));
				
				//end of session, print the transaction summary file
				frontEnd.printSummaryFile(args[1]);

			}else{
				//Tried to do a transaction before login. Print an error message.
				System.out.println("Error: not logged in");
			}
		}
		
		
		

	}
	
	/**
		Checks whether the account number passed into the argument follows the correct format conventions. 
		If further format conventions are to be added to the program, modify this methods return value.
		@param 	accountNum	a String representing the account number whose format is too be checked
		@return		A boolean, True if the account num follows the format conventions (must be 8 digits and the first digit can't be a 0). False otherwise	 
	*/
	private boolean validAccount(String accountNum){
		return (accountNum.length() == 8 && accountNum.charAt(0) != '0');
	}


	/**
		Prints all transactions made in session to the transaction summary file. The file to print into
		is specified by the String argument.
		@param 	file	A String representing the name of the file to print the transaction summary into
	*/
	private void printSummaryFile(String file){
		try{
			//Creates a new file writer, setting it up to write to the file passed into the method
			FileWriter fs = new FileWriter(file);
			//for every succesful transaction this session (except for the logout transaction)
			for(int i = 0; i < this.tempTransSummary.size()-1; i++){
				//write the summary code to the file
				fs.write(this.tempTransSummary.get(i) + "\n");
			}
			//writes the end of session code to the file
			fs.write(this.tempTransSummary.get(this.tempTransSummary.size()-1));
			//clears all the transaction codes stored in the ArrayList
			this.tempTransSummary.clear();
			//closes the file writer stream
			fs.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
		Reads all account numbers from the valid accounts file and stores them in an {@link ArrayList}.
		The method takes a {@link BufferedReader} as its only parameter to use for reading line by line from
		the valid accounts file.
		@param 	reader 	The BufferedReader setup to read from the valid accounts file
	*/
	private void readAccounts(BufferedReader reader){
		try{
			//reads the first line of the accounts file
			String str = reader.readLine();
			//while we have not reached the end of the accounts file
			while(!str.equals("00000000")){
				//add the account read into the valid accounts ArrayList
				this.validAccounts.add(str);
				//read the next line of the file
				str = reader.readLine();
			}//done reading the file
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}//End of FrontEnd Class
