/**FizzBuzz.java
 * Tomas Salaz CS251L
 * This program is meant to take in 3 Integers from the user. The 1st integer
 * being the 'Fizz" number, the 2nd being the 'Buzz" number and the last
 * being the length of the list. The output of this program will print out
 * the integers from 1 to the length of that list, if an integer in the list
 * is divisible by 'fizz' or 'buzz' that word will print out, if it is
 * divisible by both 'FizzBuz will be the output, if neither of these are the
 * case, the integer will be the output*/

public class FizzBuzz {

    /**Main Method: takes in the args and creates an Arrays that we will later
     * modify to give the desired output. There is also some error checking
     * to make sure we have the desired amount of outputs. The whole FizzBuzz
     * program will be ran out of the Main Method*/
    public static void main(String[] args) {

        //Error handling, makes sure we get the correct number of args
        if (args.length != 3){
            System.err.println("Must enter 3 numbers!");

        } else {

            //Assigns the Args to have Values, also creating x for while loop
            int fizzVal = Integer.parseInt(args[0]);
            int buzzVal = Integer.parseInt(args[1]);
            int outputLen = Integer.parseInt(args[2]);
            int x = 1;

            while (x <= outputLen){

                //4 different branches that have the possible outcomes and
                // output for each outcome
                if (x % buzzVal == 0 && x % fizzVal == 0){
                    System.out.println("FizzBuzz");
                    x++;
                }else if (x%fizzVal == 0 ){
                    System.out.println("Fizz");
                    x++;
                }else if (x%buzzVal == 0 ){
                    System.out.println("Buzz");
                    x++;
                }else{
                    System.out.println(x);
                    x++;
                }
            }
        }
    }
}
