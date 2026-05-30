public class Week6Lab {

    public static void main(String[] args) {
        int passed = 0;
        //Tests
        if(moduloMethod(25, 3) == 1) passed++;
        if(moduloMethod(625, 19) == 17) passed++;
        if(moduloMethod(26, 5) == 6) passed++;
        if(moduloMethod(31, 12) == 3) passed++;

        if(powerMethod(2, 3) == 8) passed++;
        if(powerMethod(5, 4) == 625) passed++;
        if(powerMethod(2, 8) == 256) passed++;
        if(powerMethod(6, 4) == 1296) passed++;

        //Pass or Fail
        if(passed == 8){
            System.out.println("Congrats, you're a method master!");
        }
        else {
            System.out.println("Not quite there, take another look at your implementations " + passed + "/8");
        }
    }

    public static int moduloMethod(int num, int mod){
        //This method should perform a modulo operation with num
        //and mod if num is divisible by 5, if not it should perform
        //a modulo operation with num and 3 and triple the result
        int ans = 0;
        if (num % 5 == 0) {
            ans = num % mod;
        }else{
            ans = (num % 3) * 3;
        }
        return ans;
    }

    public static int powerMethod(int base, int exp){
        //This method should calculate the value of the given base
        //raised to the specified exponent.
        int ans = 1;
        for(int n = 0; n < exp; n++){
            ans *= base;
        }
        return ans;
    }
}
