import java.io.*;
import java.util.*;

public class Tests{

  static final String outputFile = "testlog.txt";
  static final String split = "---------------------------------------------\n";
  public static void main(String[] args) throws IOException{
    FileWriter fs = new FileWriter(new File(outputFile));
    ArrayList<String[]> accounts = new ArrayList<String[]>();
    accounts.add(new String[]{"10150012", "12300", "Lucy"});
    BackEnd.accounts = accounts;
    String[][] inputs = {
      {"WD","00000000", "10150012", "-12300","Lucy"}, //Input for T3
      {"WD","00000000", "10150013", "-12300","Lucy"}, //Input for T4
      {"WD","00000000", "10150012", "-123000","Lucy"} //Input for T5
    };
    String[] expectedRes = {
      "Valid Account\nChange in balance: -12300\n",
      "Invalid Account\n",
      "Valid Account\nFailed constraint: account balance is negative\n"
    };


    //getAccount Method Tests
    fs.write("Decision coverage testing getAccount method in BackEnd.java ...\n");
    //T1: Decision 1 true
    fs.write("T1:\nExpected result is return value of 0 (array index of test account)\n");
    fs.write("Result: " + BackEnd.getAccount("10150012")+ "\n\n");
    //T2: Decision 1 false
    fs.write("T2:\nExpected result is return value of -1 (index not found)\n");
    fs.write("Result: " + BackEnd.getAccount("10150013")+ "\n" + split);
    //end of getAccount Method Tests

    //Addint Method Tests
    fs.write("Decision coverage testing addInt method in BackEnd.java ...\n");

    for(int i = 0; i < inputs.length; i++){
      fs.write("T"+(i+3)+":\nExpected result is...\n"+expectedRes[i]);
      fs.write("Results displayed on console\n\n");
      System.out.println("T"+(i+3)+" Results:");
      BackEnd.addInt(inputs[i][2],Integer.parseInt(inputs[i][3]));
      System.out.println(split);
    }
    fs.write(split);


    BackEnd.accounts.get(0)[1] = "12300";//Resets Lucy's account
    inputs = new String[][] {
      {"10150012","Lucy"},//Input for P1
      {"10150011","Tyler1"},//Input for P2
      {"10150013","Tyler2"}//Input for P3
    };

    expectedRes = new String[] {
      "[10150012, 12300, Lucy] \n",
      "]10150011, 000, Tyler1] [10150012, 12300, Lucy] \n",
      "[10150011, 000, Tyler1] [10150012, 12300, Lucy] [10150013, 000, Tyler2] \n"
    };

    fs.write("Path coverage testing for createAccount method in BackEnd.java ...");
    for(int i = 0; i < inputs.length; i++){
      fs.write("P"+(i+1)+":\nExpected result is...\n"+expectedRes[i]);
      fs.write("Results: \n");
      BackEnd.createAccount(inputs[i][0],inputs[i][1]);
      for(int j = 0; j < BackEnd.accounts.size();j++){
        fs.write(Arrays.toString(BackEnd.accounts.get(j)) + " ");
      }
      fs.write("\n\n");
    }

    //Path Coverage of createAccount
    fs.flush();
    fs.close();
  }
}
