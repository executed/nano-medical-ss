package handler;

import org.joda.time.DateTime;

public final class ParameterHandler {

    /** Checks if String is not null or empty */
    public static void checkArgs(String ... strings){
        for (String string: strings){
            if (string == null) throw new NullPointerException("String is null");
            else if (string.isEmpty()) throw new NullPointerException("String is empty");
        }
    }

    /** Checks if DateTime is not null */
    public static void checkArgs(DateTime ... dateTimes){
        for (DateTime dateTime: dateTimes){
            if (dateTime == null) throw new NullPointerException("DateTime is null");
        }
    }
}
