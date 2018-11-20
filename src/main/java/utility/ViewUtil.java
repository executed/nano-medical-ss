package utility;

public class ViewUtil {

    public static boolean referencesFile(String context){
        return (context.contains(".jsp") || context.contains(".html"));
    }
}
