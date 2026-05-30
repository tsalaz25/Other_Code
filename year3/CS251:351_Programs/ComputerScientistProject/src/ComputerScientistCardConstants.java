/**
 * This class exists to hold some constants used by the Card and
 * ComputerScientist classes.
 *
 * Use these as default values for properties that have not yet been set.
 *
 * NOTE: This class will not compile until you have created the ComputerScientist
 * class and at least implemented its two argument constructor.
 */
public class ComputerScientistCardConstants {


    /** Value to distinguish an unknown type of Computer Scientist*/
    public static final String UNKNOWN_TYPE = "UNKNOWN";

    /** Value for uninitialized years (birth, death, contribution date) */
    public static final int UNKNOWN_YEAR = -4567;

    /** Value for uninitialized accomplishment */
    public static final String UNKNOWN_CONTRIBUTION = "UNKNOWN " +
            " CONTRIBUTION";

    /** Value for unspecified computer scientist */
    public static final ComputerScientist UNKNOWN_COMPUTER_SCIENTIST =
            new ComputerScientist( "Unknown", "Computer " +
            "Scientist" );

}
