package storageContract.Exceptions;

public class StorageIsFullException extends Throwable {
    public StorageIsFullException(String error_message) {
        super(error_message);
    }
}
