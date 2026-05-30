/**
 * Author: Tomas Salaz
 * Part of Lab #3: Inheritance for CS251L
 */
public class Candy extends Dessert{
    double weight;
    double price;

    /**
     *
     * @param name argument for the name of the candy
     * @param weight argument for the weight, in pounds of the candy
     * @param price argument for the price, in dollar/lb of the candy
     */
    public Candy(String name, double weight, double price){
        super(name);
        this.weight = weight;
        this.price = price;
    }

    //Getter and Setter Methods
    public double getWeightInPounds(){
        return weight;
    }
    public double getPricePerPound(){
        return price;
    }
    public double getPrice() {
        return weight * price;
    }
}