import java.util.ArrayList;
import java.time.LocalDate;

public class Transaction {
    // Set relevant instance variables
    private LocalDate date;
    private double value;
    private ArrayList<String> categories;

    public Transaction (LocalDate date, double value, ArrayList<String> categories) {
        this.date = date;
        this.value = value;
        this.categories = categories;
    }
    
    /**
     * Returns the date of transaction
     * Pre-condition: Transaction object initialized
     * Post-condition: Date of transaction returned
     * 
     * @return date     date of transaction
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the value of the transaction
     * Pre-condition: Transaction object initialized
     * Post-condition: Value of transaction returned
     * 
     * @return value     value of transaction
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns the category list of the transaction
     * Pre-condition: Transaction object initialized
     * Post-condition: Categories of transaction returned
     * 
     * @return categories     categories of transaction
     */
    public ArrayList<String> getCategories() {
        return categories;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
}