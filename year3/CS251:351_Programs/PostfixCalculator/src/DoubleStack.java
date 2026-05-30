/**
 * Author: Tomas Salaz  CS251L  DoubleStack.java for Lab 5:Postfix Calculator
 *
 * DoubleStack.Java implements the given Stack interface methods. This Class
 * creates a Stack using a LinkedList that will be used to store operands.
 */

import java.util.LinkedList;
public class DoubleStack implements Stack<Double>{
    public LinkedList<Double> stack = new LinkedList<>();
    @Override
    public boolean isEmpty() {
        boolean res = false;
        if (stack.size()>0) {res = true;}
        return res;
    }
    @Override
    public void push(Double val) {
        stack.push(val);
    }
    @Override
    public Double pop() {
        return stack.pop();
    }
    @Override
    public Double peek() {
        return stack.peek();
    }
}