/**
 * Author: Tomas Salaz   CS251L
 * BankAccount Class, use with BankingTest.java and
 * InsufficientFundsExceptions.java. Used to make Bank Account Objects
 */
public class BankAccount {
    //Member Variables
    public int id;
    public double balance;
    public double sf;

    //Bank Account Constructor
    public BankAccount(int id){
        this.id = id;
        balance = 0.0;
    }

    //Getter Methods
    public int getAccountNumber(){
        return id;
    }
    public double getBalance(){
        return balance;
    }

    /**
     * @param d is the deposit amount, cannot be negative, if it is, an
     * exception is thrown
     */
    public void deposit (double d){
        if (d > 0) {
            balance += d;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @param w is the withdraw amount, cannot be negative and the account has
     * to have enough money, else, exceptions are thrown
     * @throws InsufficientFundsException is a custom exception called when
     * there are insufficient funds.
     */
    public void withdraw (double w) throws InsufficientFundsException {
        if (w < balance && w > 0){
            balance-=w;
        } else if (w > balance){
            sf = w - balance;
            throw new InsufficientFundsException(sf);
        } else {
            throw new IllegalArgumentException();
        }
    }
}