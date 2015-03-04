package ro.teamnet.bootstrap.exception;

/**
 * Exception class used by between filter
 *
 * @author Mihaela Petre
 */
public class InvalidNumberOfFiltersException extends Exception {
    public InvalidNumberOfFiltersException(String s) {
        System.out.println(s);
    }
}
