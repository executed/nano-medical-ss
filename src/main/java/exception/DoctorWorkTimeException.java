package exception;

public class DoctorWorkTimeException extends Exception{

    private final int code;

    public DoctorWorkTimeException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode(){ return this.code; }
}
