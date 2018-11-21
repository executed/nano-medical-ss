package exception;

public class ValidationException extends RuntimeException{

    public ValidationException(String className){
        throw new RuntimeException("Validation failed: " + className);
    }
}
