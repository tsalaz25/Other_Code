public class Mid22Tests {
    //    public static void main ( String [] args ) {
//        int apple = 6;
//        int orange = 4;
//        int banana = 12;
//        if ( apple < banana ) {
//            System . out . println ( " one " );
//            if ( orange % 4 == 0) {
//                System . out . println ( " two " );
//            } else {
//                System . out . println ( " buckle " );
//            }
//            System . out . println ( " my " );
//        } else if ( orange <= apple ) {
//            System . out . println ( " shoe " );
//            if ( banana - orange < apple ) {
//                System . out . println ( " three " );
//            }
//        } else {
//            System . out . println ( " four " );
//        }
//        switch ( orange + apple ) {
//            case 4:
//            case 6:
//            case 12:
//                System . out . println ( " shut " + apple );
//                break ;
//            case 15:
//            case 10:
//                System . out . println ( " the " + orange );
//            default :
//                System . out . println ( " door " + banana );
//        }
//    }
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
        int n = 6;
        System.out.println(" A : n = " + n);
        for (int i = 2 * n; i < 20; i += 5) {
            n = foo(i);
            System.out.println(" B : i = " + i + " , n = " + n);
        }
        System.out.println(" C : n = " + n);
    }
}
