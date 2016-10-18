import java.util.*;
import java.io.*;
public class FrontEnd{
	
	private ArrayList<String> validAccounts = new ArrayList<String>();
	private ArrayList<String> tempTransSummary = new ArrayList<String>();
	
	public static void main(String args[]) throws IOException{

		FrontEnd frontEnd = new FrontEnd();
		
		BufferedReader transactions = new BufferedReader(new InputStreamReader(System.in));
		FileWriter summaryFile = null;
		try{
			frontEnd.readAccounts(new BufferedReader(new FileReader(new File(args[0]))));
			summaryFile = new FileWriter(args[1]);
		}catch(IOException e){
		}
		
		String[] transaction;
		
		while(true){
			transaction = frontEnd.nextTransaction(transactions);
			if(transaction == null){
				break;
			}
			if(transaction[0].equals("login")){
				Object user;
				if(transaction[1].equals("atm")){
					user = new Atm();
				}else{
					user = new Agent();
				}
				do{
					transaction = frontEnd.nextTransaction(transactions);
					if (transaction == null){
						break;	
					}
					
					switch(transaction[0]){
						case "create":	frontEnd.tempTransSummary.add(((Agent)user).create(Integer.parseInt(transaction[1]),transaction[2]));
										break;
						case "delete":	frontEnd.tempTransSummary.add(((Agent)user).delete(Integer.parseInt(transaction[1]),transaction[2]));
										break;
						case "deposit": if(user instanceof Atm){
											frontEnd.tempTransSummary.add(((Atm)user).deposit(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2])));
										}else{
											frontEnd.tempTransSummary.add(((Agent)user).deposit(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2])));
										}
										break;
						case "withdraw": if(user instanceof Atm){
											frontEnd.tempTransSummary.add(((Atm)user).withdraw(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2])));
										}else{
											frontEnd.tempTransSummary.add(((Agent)user).withdraw(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2])));
										}
										break;
						case "transfer": if(user instanceof Atm){
											frontEnd.tempTransSummary.add(((Atm)user).transfer(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2]),Integer.parseInt(transaction[3])));
										}else{
											frontEnd.tempTransSummary.add(((Agent)user).transfer(Integer.parseInt(transaction[1]),Integer.parseInt(transaction[2]),Integer.parseInt(transaction[3])));
										}
										break;
						case "logout":	user = null;
										frontEnd.tempTransSummary.add("ES");
										break;
						default:
								System.out.println("Invalid transaction");
						
					}
				}while(!transaction[0].equals("logout"));
				
				frontEnd.printSummaryFile(summaryFile);

			}
		}
		
		
		
	
	}
	
	
	private void printSummaryFile(FileWriter fs){
		try{
			for(int i = 0; i < tempTransSummary.size()-1; i++){
				fs.write(tempTransSummary.get(i) + "\n");
			}
			fs.write(tempTransSummary.get(tempTransSummary.size()-1));
			tempTransSummary.clear();
		}catch(IOException e){
		}
	}
	
	private void readAccounts(BufferedReader reader){
		try{
			String str = reader.readLine();
			while(!str.equals("00000000")){
				validAccounts.add(str);
				str = reader.readLine();
			}
		}catch(IOException e){
		}
	}
	
	private String[] nextTransaction(BufferedReader reader){
		String str = null;
		try{
			str = reader.readLine();
		}catch(IOException e){}
		if(str!=null){
			return str.split(" ");
		}
		return null;
	}
	
}
