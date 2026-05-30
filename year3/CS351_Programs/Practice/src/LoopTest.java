public class LoopTest {
    public static int foo(int a) {
        int i = a % 10;
        int n = a / 4;
        if (n < i) {
            n += n;
        }
        System.out.println(" foo : i = " + i + " , n = " + n);
        return i + n;
    }

    public static void main(String[] args) {
        int n = 3;
        System.out.println(" A : n = " + n);
        for (int i = 2 * n; i < 25; i += 5) {
            n = foo(i);
            System.out.println(" B : i = " + i + " , n = " + n);
        }
        System.out.println(" C : n = " + n);
    }
}