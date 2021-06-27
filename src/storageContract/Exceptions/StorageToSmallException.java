package storageContract.Exceptions;

public class StorageToSmallException extends Throwable {
    public StorageToSmallException(String error_message){
        super(error_message);
    }
}
