/**
 * Author: Tomas Salaz   CS251L
 * Exception class for Exceptions Lab!
 */
public class InsufficientFundsException extends Exception{
    //Member Variable
    private double shortfall;

    /**
     * @param shortfall is passed from BankAccount.java file and is stored.
     * shortfall is the amount short for the payment. The Super calls the
     * exceptions constructor and allows to implement a custom error message.
     */
    public InsufficientFundsException (double shortfall){
        super("You need more money!");
        this.shortfall = shortfall;
    }

    //Getter Method
    public double getShortfall (){
        return shortfall;
    }
}