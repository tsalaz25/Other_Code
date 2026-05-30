/**
 * Author: Tomas Salaz
 * Part of Lab #3: Inheritance for CS251L
 */
public class Sundae extends IceCream{
    Dessert topping;

    /**
     *
     * @param base called from IceCream Class, inherits only IceCream and not
     *Dessert
     * @param topping argument to create the topping for the sunday.
     */
    public Sundae (IceCream base, Dessert topping){
        super(base.name + " topped with " +topping.name, base.getPrice());
        this.topping = topping;

        price = base.getPrice() + topping.getPrice();
    }
}