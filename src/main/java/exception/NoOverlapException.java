package exception;

public class NoOverlapException extends RuntimeException{

    int code;

    public NoOverlapException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode(){ return this.code; }
}
