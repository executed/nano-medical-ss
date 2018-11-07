package service;

import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static constant.STR_CONSTANT.PATH_PROPERTIES;
import static constant.STR_CONSTANT.PATH_SQL_QUERIES;
import static org.apache.logging.log4j.LogManager.getLogger;
import static service.PropertiesService.Type.*;
import static utility.ClassNameUtil.getClassName;

public class PropertiesService {

    public enum Type { DEFAULT, SQL_QUERY }

    private static final Logger LOGGER = getLogger(getClassName());

    private static HashMap<Type, Properties> propertyMap = new HashMap<>();
    private static HashMap<Type, String> typePathMap = new HashMap<Type, String>(){{
        put(DEFAULT, PATH_PROPERTIES);
        put(SQL_QUERY, PATH_SQL_QUERIES);
    }};

    static {
        LOGGER.trace("Started property files initialization");
        typePathMap.forEach((x, y) -> propertyMap.put(x, loadProperties(y)));
        LOGGER.info("Property files initialization success");
    }

    public static String getPropertyByKey(String key){
        return propertyMap.get(DEFAULT).getProperty(key);
    }

    public static String getPropertyByKey(String key, Type type){
        return propertyMap.get(type).getProperty(key);
    }

    private static Properties loadProperties(String path){
        Properties property = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(path);
            property.load(inputStream);
        }
        catch (IOException e) {
            LOGGER.error("Property file wasn't loaded: " + path, e);
        }
        return property;
    }
}