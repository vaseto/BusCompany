package dbLayer;

/**
 * Created by Vasil Nedelchev on 27.4.2016 г..
 */
public class DuplicateException extends  Exception {
    public DuplicateException() {

    }
    public DuplicateException(String massage) {
        super(massage);
    }
    public DuplicateException(Throwable cause) {
        super (cause);
    }
    public DuplicateException(String message, Throwable cause) {
        super (message, cause);
    }

}
