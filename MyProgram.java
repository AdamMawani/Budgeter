import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MyProgram
{
    public static void main(String[] args)
    {
        try {
            // Instantiate new budgeter object of the Budgeter class.
            Budgeter budgeter = new Budgeter(1000.00, 0.10);
            
            // Read text from transactionHistory.txt and save lines as transactions
            Scanner fileReader = new Scanner (new File ("transactionHistory.txt"));
            while (fileReader.hasNextLine()) {
                String transaction = fileReader.nextLine();
                if (!transaction.isEmpty()) {
                    budgeter.record(transaction);
                }
            }
            fileReader.close();
            
            // Print out budget summary
            budgeter.printBudget("budgetSummary.txt");
            System.out.println("Budget summary successfully printed to budgetSummary.txt");
        }
        catch (IOException exception) {
            // Report potential errors
            System.err.println("Error reading or writing file. " + exception.getMessage());
        }
    }
}