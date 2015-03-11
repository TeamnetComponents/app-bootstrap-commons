package ro.teamnet.bootstrap.exception;

/**
 * A <em>generic</em> exception to be extended by all exceptions from <em>Teamnet's Bootstrap Initiative&trade; Program</em> in order to provide a clean
 * exception hierarchy, to be easily intercepted at run time using {@code AOP} or other means. This class is just an extension
 * of {@link java.lang.RuntimeException} overriding all its constructors, and thus your exception will not need to
 * implement them all.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-11
 */
public abstract class ApplicationException extends RuntimeException {

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException() {
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
