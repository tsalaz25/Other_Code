public class MethodTest {
    public static String foo ( int a , String [] strs ) {
        int b = strs [ a ]. length () % strs . length ;
        String c = strs [ b ];
        System . out . println ( " a = " + a + " , b = " + b + " , c = " + c );
        if ( a > b ) {
            strs [ a ] = " final " ;
        } else {
            strs [ b ] = " exam " ;
        }
        return strs [ a ] + " , " + strs [ b ];
    }
    public static void main ( String [] args ) {
        int a = 3;
        int b = 1;
        String [] c = new String []{ " yay " , " summer " , " break " , " time " };
        System . out . println ( a + " , " + foo (a , c ));
        System . out . println ( b + " , " + foo (b , c ));
        for ( int i = 0; i < c . length ; i ++) {
            System . out . println ( i + " , " + c [ i ]);
        }
    }
}
