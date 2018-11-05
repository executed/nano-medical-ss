package exception;

public class TimeSlotHandleException extends RuntimeException{

    private final int code;

    public TimeSlotHandleException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode(){ return this.code; }
}
