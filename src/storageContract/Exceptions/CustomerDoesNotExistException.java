package storageContract.Exceptions;

public class CustomerDoesNotExistException extends Throwable {
    public CustomerDoesNotExistException(String error_message) {
        super(error_message);
    }
}
