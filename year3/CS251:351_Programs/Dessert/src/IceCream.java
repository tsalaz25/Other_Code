/**
 * Author: Tomas Salaz
 * Part of Lab #3: Inheritance for CS251L
 */
public class IceCream extends Dessert{
    double price;

    /**
     *
     * @param name argument to get the flavor from the user
     * @param price called from Desser class to get price
     */
    public IceCream (String name, double price){
        super(name);
        this.price = price;
    }

    //Getter method
    public double getPrice() {return price;}
}