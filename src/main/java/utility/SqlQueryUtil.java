package utility;

import service.PropertiesService;

import static constant.STR_CONSTANT.PATH_SQL_QUERIES;

public class SqlQueryUtil {

    private static PropertiesService propertiesService = new PropertiesService();

    public static String getQuery(String key){
        return propertiesService.getPropertyByKey(key, PATH_SQL_QUERIES);
    }
}
