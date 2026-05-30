public class euclidean {
    public static void main (String[] args){
        GCD(10,11);
        GCD(21,72);
        GCD(21, 44);
        GCD(36,48);
        GCD(123,277);
        GCD(123,2347);
        GCD(3454,4666);
        GCD(9999,11111);
        GCD(60613,988765);
        GCD(606132,988765);
    }
    public static void GCD(int a, int x){
       int ai = 1;
       int bi = 0;
       int r = a % x;
       int gcd = x;
       int t1, t2, t3;
       int quo;
       while (r != 0){
           t1 = r;
           r = gcd % r;
           gcd = t1;
       }
       int x1 = 0;
       int y1 = 1;
       int r1 = a;
       int r2 = x;
       while(r2 != 0){
           quo = r1/r2;
           t1 = r2;
           r2 = r1 - quo * r2;
           r1 = t1;
           t2 = x1;
           x1 = ai - quo * x1;
           ai = t2;
           t3 = y1;
           y1 = bi - quo * y1;
           bi = t3;
       }
       System.out.printf("%d = (%d)*%d + (%d)*%d\n", gcd,ai,a,bi,x);
    }
}
