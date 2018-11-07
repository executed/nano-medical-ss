package utility;

import service.PropertiesService;
import service.PropertiesService.Type;

public class SqlQueryUtil {

    public static String getQuery(String key){
        return PropertiesService.getPropertyByKey(key, Type.SQL_QUERY);
    }
}
