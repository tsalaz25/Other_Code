public class BaseConv {
    public static String  basicConv(int q, int b, String a) {
       if (q == 0){
           return new StringBuilder(a).reverse().toString();
       } else {
           int rem = q % b;
           a += Integer.toString(rem);
           return basicConv(q/b, b, a);
       }
    }

    public static void main (String[] args){
        int[] nums = {9,54,206,525};
        int[] basicBase = {2,3,4,5,6,7,8,9,10};

        for (int n : nums){
            for (int b : basicBase){
                System.out.println(n + " in base " + b + ": " + basicConv(n,b,""));
            }
        }
    }
}