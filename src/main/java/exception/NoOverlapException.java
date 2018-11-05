package exception;

public class NoOverlapException extends RuntimeException{

    private final int code;

    public NoOverlapException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode(){ return this.code; }
}
