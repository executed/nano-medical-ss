package service;

public class ViewService {

    public static boolean referencesFile(String context){
        return (context.contains(".jsp") || context.contains(".html"));
    }
}
