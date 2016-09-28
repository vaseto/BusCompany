package dbLayer;

/**
 * Created by Vasil Nedelchev on 27.4.2016 г..
 */
public class DuplicateRoutesException extends  Exception {
    public DuplicateRoutesException() {

    }
    public DuplicateRoutesException(String massage) {
        super(massage);
    }
    public DuplicateRoutesException (Throwable cause) {
        super (cause);
    }
    public DuplicateRoutesException (String message, Throwable cause) {
        super (message, cause);
    }

}
