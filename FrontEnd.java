import java.util.*;
import java.io.*;
public class FrontEnd{
	
	private ArrayList<String> validAccounts = new ArrayList<String>();
	private ArrayList<String> tempTransSummary = new ArrayList<String>();
	
	public static void main(String args[]) throws IOException{
		
		readAccounts(new BufferedReader(new FileReader(new InputStreamReader(args[0]))));
		
		BufferedReader transactions = new BufferedReader(new new InputStreamReader(System.in));
		FileWriter summaryFile = new FileWriter(args[1]);
		
		String[] transaction;
		
		while(true){
			transaction = nextTransaction(transactions);
			if(transaction == null){
				break;
			}
			if(transaction[0].equals("login")){
				Object user;
				if(transaction[1].equals("atm")){
					user = new Atm();
				}else if (transaction[1].equals("agent")){
					user = new Agent();
				}
				do{
					transaction = nextTransaction(transactions);
					if (transaction == null){
						break;	
					}
					switch(transaction[0]){
						case "create":	tempTransSummary.add(((Agent)user).create(Integer.parseInt(transaction[1]),transaction[2]));
										break;
						case "delete":	tempTransSummary.add(((Agent)user).delete(Integer.parseInt(transaction[1]),transaction[2]));
										break;
						case "deposit": if(user instanceof Atm){
											tempTransSummary.add(((Atm)user).deposit(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2])));
										}else{
											tempTransSummary.add(((Agent)user).deposit(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2])));
										}
										break;
						case "withdraw": if(user instanceof Atm){
											tempTransSummary.add(((Atm)user).withdraw(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2])));
										}else{
											tempTransSummary.add((Agent)user).withdraw(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2])));
										}
										break;
						case "transfer": if(user instanceof Atm){
											tempTransSummary.add((Atm)user).transfer(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2]),Integer.parseInt(transaction[3])));
										}else{
											tempTransSummary.add((Agent)user).transfer(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2]),Integer.parseInt(transaction[3])));
										}
										break;
						case "logout":	user = null;
										tempTransSummary.add("ES");
										break;
						default:
								System.out.println("Invalid transaction");
						
					}
				}while(!transaction[0].equals("logout"));
				
				printSummaryFile(summaryFile);

			}
		}
		
		
		
	
	}
	
	
	public void printSummaryFile(FileWriter fs){
		for(int i = 0; i < tempTransSummary.size()-1; i++){
			fs.write(tempTransSummary.get(i) + "\n");
		}
		fs.write(tempTransSummary.get(tempTransSummary.size()-1));
		tempTransSummary.clear();
	}
	
	public void readAccounts(BufferedReader reader){
		String str = reader.readLine();
		while(!str.equals("00000000")){
			validAccounts.add(str);
			str = reader.readLine();
		}
	}
	
	public String[] nextTransaction(BufferedReader reader){
		String str = reader.readLine();
		if(str!=null){
			return str.split(' ');
		}
		return null;
	}
	
}
