import java.util.ArrayList;
import java.time.LocalDate;

public class Expense extends Transaction {
    // Set relevant instance variable
    private String vendor;
    
    /** 
     * Constructs a new Expense object and passes parameters to the Transaction super class
     * 
     * @param date          date of expense
     * @param value         value of expense
     * @param categories    categories of expense
     * @param vendor        vendor for the expense
     */
    public Expense (LocalDate date, double value, ArrayList<String> categories, String vendor) {
        super(date, -Math.abs(value), categories);
        this.vendor = vendor;
    }

    /**
     * Returns the vendor for the expense
     * Pre-condition: Expense object initialized
     * Post-condition: Vendor for the expense returned
     * 
     * @return vendor     vendor for the expense
     */
    public String getVendor() {
        return vendor;
    }
}