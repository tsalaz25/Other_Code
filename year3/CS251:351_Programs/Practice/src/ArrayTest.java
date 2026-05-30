public class ArrayTest {
    public static int foo(int a, int[] vals) {
        int b = vals[a] % 7;
        System.out.println(" foo : " + vals[a] + " , a = " + a + " , b = " + b);
        vals[a] = b;
        return a + b;
    }

    public static void main(String[] args) {
        int a = 4;
        int b = 2;
        int c = 0;
        int[] arr = {12, 24, 36, 48, 60, 72};
        int x = foo(a, arr);
        System.out.println(" main1 : " + a + " , " + x);
        x = foo(b, arr);
        System.out.println(" main2 : " + b + " , " + x);
        x = foo(c, arr);
        System.out.println(" main3 : " + c + " , " + x);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(" arr [ " + i + " ] = " + arr[i]);
        }
        //System.out.println(" arr : " + Arrays.toString(arr));
    }
}