package utility;

public class ClassName {

    public static String getClassName(){
        try {
            throw new Exception();
        } catch (Exception e) {
            return e.getStackTrace()[0].getClassName();
        }
    }
}
