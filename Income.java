import java.time.LocalDate;
import java.util.ArrayList;

public class Income extends Transaction {
    // Set relevant instance variables
    private String source;
    private boolean taxFree;
    
    /** 
     * Constructs a new Income object and passes parameters to the Transaction super class
     * 
     * @param date          date of income receipt
     * @param value         value of income
     * @param categories    categories of income
     * @param source        source of income
     * @param taxFree       whether income is tax-free
     */
    public Income (LocalDate date, double value, ArrayList<String> categories, String source, boolean taxFree) {
        super(date, Math.abs(value), categories);
        this.source = source;
        this.taxFree = taxFree;
    }
    
    /**
     * Returns the source of the income
     * Pre-condition: Income object initialized
     * Post-condition: Source of income returned
     * 
     * @return source     source of income
     */
    public String getSource() {
        return source;
    }

    /**
     * Returns whether the income is taxFree
     * Pre-condition: Income object initialized
     * Post-condition: True returned is tax free, false otherwise
     * 
     * @return taxFree     boolean representing if income is tax-free
     */
    public boolean isTaxFree() {
        return taxFree;
    }
}