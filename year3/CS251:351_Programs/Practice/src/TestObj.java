public class TestObj {
    private int x ;
    public TestObj ( int x ) {
        this . x = x ;
    }
    public void printAndChange ( int x ) {
        System . out . println ( this . x );
        System . out . println ( x );
        this . x += x ;
    }
    public static void main ( String [] args ) {
        int x = 2;
        TestObj first = new TestObj ( x );
        first . printAndChange ( x * 2);
        first . printAndChange ( x + 3);
        --x ;
        TestObj second = new TestObj ( x );
        x += 7;
        second . printAndChange ( x );
        x /= 3;
        second . printAndChange ( x );
        x ++;
        first . printAndChange ( x );
    }
}
