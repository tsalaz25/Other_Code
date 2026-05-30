/**
 * Author: Tomas Salaz
 * Part of Lab #3: Inheritance for CS251L
 */
public class Cookie extends Dessert{
    int count;
    double price;

    /**
     *
     * @param name arguments for the type of the cookie
     * @param count argument for the amount of cookies
     * @param price argument for the price of cookie in Dollars/Cookie
     */
    public Cookie(String name, int count, double price){
        super (name);
        this.count = count;
        this.price = price;
    }

    //Getter and Setter methods
    public int getItemCount(){return count;}
    public double getPricePerDozen(){return price;}
    public double getPrice() {return count/price;}
}