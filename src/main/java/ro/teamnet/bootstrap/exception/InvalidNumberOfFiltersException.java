package ro.teamnet.bootstrap.exception;

/**
 * Exception class used by between filter
 *
 * @author Mihaela Petre
 * @author Bogdan.Stefan
 */
public class InvalidNumberOfFiltersException extends ApplicationException {

    public InvalidNumberOfFiltersException(String message) {
        super(message);
        System.out.println(message);
    }
}
