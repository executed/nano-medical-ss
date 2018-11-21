package utility;

import entity.View;

public class ViewUtil {

    public static boolean referencesFile(String context){
        return (context.contains(".jsp") || context.contains(".html"));
    }

    public static View getForwardedView(String path){
        View view = new View(true);
        view.setPath(path);
        return view;
    }

    public static View getRedirectedView(String path){
        View view = new View();
        view.setPath(path);
        return view;
    }
}
