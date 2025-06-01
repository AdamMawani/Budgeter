import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
import java.time.LocalDate;
// Learned to use LocalDate and IOException from Oracle Docs

public class Budgeter {
    // Set relevant instance variables
    private ArrayList<Transaction> transactions;
    private double savingGoal;
    private double taxRate;
    
    /** 
     * Constructs a new Budgeter object without a complete transaction list
     * 
     * @param savingGoal    goal for total saving in budget
     * @param taxRate       rate of taxation on income
     */
     public Budgeter (double savingGoal, double taxRate) {
        this.savingGoal = savingGoal;
        this.taxRate = taxRate;
        transactions = new ArrayList<Transaction>();
    }

    /** 
     * Constructs a new Budgeter object with a complete transaction list
     * 
     * @param savingGoal    goal for total saving in budget
     * @param taxRate       rate of taxation on income
     * @param transactions  list of transactions
     */
    public Budgeter (double savingGoal, double taxRate, ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        this.savingGoal = savingGoal;
        this.taxRate = taxRate;
    }
    
    /**
     * Records a new transaction in the transactions list
     * Pre-condition: Budgeter object initialized
     * Post-condition: transaction is recorded in the list of transactions
     * 
     * @param transactionLine     String representing transaction
     */
    public void record (String transactionLine) {
        // Stores all elements of the formatted transactionLine
        ArrayList<String> transactionElements = new ArrayList<String> (Arrays.asList(transactionLine.split(" ")));
        Boolean type = transactionElements.get(0).equals("Income");
        double value = Double.parseDouble(transactionElements.get(1));
        LocalDate date = LocalDate.parse(transactionElements.get(2));
        ArrayList<String> categories = new ArrayList<String> (Arrays.asList(transactionLine.split(";")));
        
        // Stores income-specific parameters and creates a new Income object
        if (type) {
            String source = transactionElements.get(4);
            boolean taxFree = Boolean.parseBoolean(transactionElements.get(5));
            transactions.add(new Income(date, value, categories, source, taxFree));
        }
        
        // Stores expense-specific parameters and creates a new Expense object
        else{
            String vendor = transactionElements.get(4);
            transactions.add(new Expense(date, value, categories, vendor));
        }
    }
    
    /**
     * Recursively computes the net value of transactions
     * Pre-condition: Budgeter object initialized
     * Post-condition: Net value of transactions is returned
     * 
     * @param index     current index
     * @return          sum of values from index to end of list
     */
    public double netValue(int index) {
        if (index >= transactions.size()) {
            return 0;
        }
        
        Transaction transaction = transactions.get(index);
        double value = transaction.getValue();
        
        if (transaction instanceof Income) {
            Income income = (Income) transaction;
            if (!income.isTaxFree()) {
                value *= (1-taxRate);
            }
        }
        return value + netValue(index+1);
    }
    
    /**
     * Sorts transactions by date using selection sort
     * Pre-condition: Budgeter object initialized, transactions list filled
     * Post-condition: Sorted list of transactions returned
     * 
     * @return sorted       List of transactions sorted by date
     */
    public ArrayList<Transaction> sortByDate() {
        ArrayList<Transaction> sorted = new ArrayList<Transaction>(transactions);
        // Implements selection sort based on date
        for (int i = 0; i < sorted.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j< sorted.size(); j++) {
                if (sorted.get(j).getDate().isBefore(sorted.get(minIndex).getDate())) {
                    minIndex = j;
                }
            }
            Transaction temp = sorted.get(i);
            sorted.set(i, sorted.get(minIndex));
            sorted.set(minIndex, temp);
        }
        return sorted;
    }
    
    /**
     * Sorts transactions by value and returns the largest incomes and expenses
     * Pre-condition: Budgeter object initialized, transactions list filled
     * Post-condition: List of top 5 incomes and expenses returned
     * 
     * @return topFive       List of top five incomes and expenses by value
     */
    public ArrayList<Transaction> topValues() {
        // Separates incomes and expenses into unique lists
        ArrayList<Income> incomes = new ArrayList<Income>();
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        for (Transaction transaction : transactions) {
            if (transaction instanceof Income) {
                incomes.add((Income) transaction);
            }
            else if (transaction instanceof Expense) {
                expenses.add((Expense) transaction);
            }
        }
        
        // Employs selection sort to sort incomes by value
        for (int i = 0; i < incomes.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < incomes.size(); j++) {
                if (incomes.get(j).getValue() > incomes.get(maxIndex).getValue()) {
                    maxIndex = j;
                }
            }
            Income temp = incomes.get(i);
            incomes.set(i, incomes.get(maxIndex));
            incomes.set(maxIndex, temp);
        }
        
        // Employs selection sort to sort expenses by value
        for (int i = 0; i < expenses.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < expenses.size(); j++) {
                if (expenses.get(j).getValue() > expenses.get(maxIndex).getValue()) {
                    maxIndex = j;
                }
            }
            Expense temp = expenses.get(i);
            expenses.set(i, expenses.get(maxIndex));
            expenses.set(maxIndex, temp);
        }
        
        // Adds top five incomes and expenses by value to new list, which is returned
        ArrayList<Transaction> topFive = new ArrayList<>();
        for (int i = 0; i < 5 && i < incomes.size(); i++)  {
            topFive.add(incomes.get(i));
        }
        for (int i = expenses.size() - 1; i > expenses.size() - 6; i--) {
            topFive.add(expenses.get(i));
        }
        
        return topFive;
    }

    /**
     * Write budget summary in a designated file
     * Pre-condition: Budgeter object initialized, file created
     * Post-condition: Budget summary written to designated file
     * 
     * @param fileName      Name of file to write to
     */    
    public void printBudget (String fileName) throws IOException {
        // Creates FileWriter to write to the file
        FileWriter budgetWriter = new FileWriter (fileName);
        
        // Calculated net value of budget using netValue method
        double total = netValue(0);
        
        // Determines if saving goal was reached
        if (total >= savingGoal) {
            budgetWriter.write("Saving goal accomplished! You saved $" + Math.round(total * 100.00)/100.00);
        }
        else {
            budgetWriter.write("Saving goal not accomplished. The net value of your transactions was " + total);
        }
        
        // Writes top 5 incomes and expenses for review
        budgetWriter.write("\nTop five incomes and expenses:\n");
        ArrayList<Transaction> top = topValues();
        for (Transaction transaction : top) {
            budgetWriter.write(transaction.getDate() + " | " + transaction.getValue() + " | " + transaction.getCategories() + "\n");
        }
        
        // Writes a list of transactions by date
        budgetWriter.write("\nTransactions by date:\n");
        ArrayList<Transaction> sorted = sortByDate();
        for (Transaction transaction : sorted) {
            budgetWriter.write(transaction.getDate() + " | " + transaction.getValue() + " | " + transaction.getCategories() + "\n");
        }
        budgetWriter.close();
        
    }
}