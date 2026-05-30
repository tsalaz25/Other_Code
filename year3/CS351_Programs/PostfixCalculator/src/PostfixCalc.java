/**
 * Author: Tomas Salaz  CS251L  PostfixCalc.java for Lab 5:Postfix Calculator
 *
 * Makes a PostfixCalc calc and uses some nested classes and methods to create
 * a working postfix calculator
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PostfixCalc {
    /**
     * Member Variables
     *DoubleStack creates a Stack of doubles named number, that stores operands
     *operatorMap is a Map, that will later become a hashmap, that is initialized
     *in the Constructor.
     */
    private Stack<Double> number = new DoubleStack();
    private Map<String, Operator> operatorMap;

    /**
     * Constructor to create a PostfixCalc object. Creates an operator hashmap
     * and assigns operators to the necessary class and its
     * methods(implementation).
     */
    public PostfixCalc() {
        operatorMap = new HashMap<>();
        operatorMap.put("+" , new addition());
        operatorMap.put("-" , new subtraction());
        operatorMap.put("*" , new multiplication());
        operatorMap.put("/" , new division());
        operatorMap.put("=" ,  new print());
        operatorMap.put("add" , new addition());
        operatorMap.put("sub" , new subtraction());
        operatorMap.put("mult" , new multiplication());
        operatorMap.put("div" , new division());
        operatorMap.put("print" , new print());
    }

    /**
     * @param d
     * Stores the operand, d, into the numbers stack (Member Variable).
     */
    public void storeOperand(double d) {
        number.push(d);}

    /**
     * @param s
     * Creates LinkedList to Temporarily store operands to evaluate. The argument,
     * s, is the passed from the scanner and is used to call the evaluation
     * assigned to the string.
     */
    public void evalOperator(String s) {
        LinkedList<Double> tempEvalList = new LinkedList<>();
        Operator operation = operatorMap.get(s);
        //Fills Temporary list with doubles
        for (int i = 0; i < operation.numArgs(); i++){
            tempEvalList.push(number.pop());
        }
        //Evaluates expression and pushes it back into number stack
        number.push(operation.eval(tempEvalList));

        //only prints if the expression ends
        if (s.equals("=") || s.equals("print")){
            System.out.println(number.peek());
        }
    }

    /**
     * Nested Classes. All implement Operator interface and used to evaluate
     * expressions, those expressions being +, -, *, /, =, add, sub, mult, div,
     * and print.
     */
    public static class print implements Operator  {
        @Override
        public int numArgs() {return 1;}
        @Override
        public double eval(List<Double> args) {
            return args.get(0);
        }
    }
    public static class addition implements Operator  {
        @Override
        public int numArgs() {return 2;}
        @Override
        public double eval(List<Double> args) {
            return args.get(0) + args.get(1);
        }
    }
    public static class subtraction implements Operator  {
        @Override
        public int numArgs() {return 2;}
        @Override
        public double eval(List<Double> args) {
            return args.get(0) - args.get(1);
        }
    }
    public static class multiplication implements Operator  {
        @Override
        public int numArgs() {return 2;}
        @Override
        public double eval(List<Double> args) {
            return args.get(0) * args.get(1);
        }
    }
    public static class division implements Operator  {
        @Override
        public int numArgs() {return 2;}
        @Override
        public double eval(List<Double> args) {
            return args.get(0) / args.get(1);
        }
    }
}