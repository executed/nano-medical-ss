package service;

import constant.LOG_MSG_CONSTANT;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import static constant.LOG_MSG_CONSTANT.PROP_INIT;
import static constant.LOG_MSG_CONSTANT.PROP_INIT_FAIL;
import static constant.LOG_MSG_CONSTANT.PROP_INIT_OK;
import static constant.PATH_CONSTANT.PROPERTIES;
import static constant.PATH_CONSTANT.SQL_QUERIES;
import static org.apache.logging.log4j.LogManager.getLogger;
import static service.PropertiesService.Type.*;
import static utility.ClassNameUtil.getClassName;

public class PropertiesService {

    public enum Type { DEFAULT, SQL_QUERY }

    private static final Logger LOGGER = getLogger(getClassName());

    private static HashMap<Type, Properties> propertyMap = new HashMap<>();
    private static HashMap<Type, String> typePathMap = new HashMap<Type, String>(){{
        put(DEFAULT, PROPERTIES);
        put(SQL_QUERY, SQL_QUERIES);
    }};

    static {
        LOGGER.trace(PROP_INIT);
        typePathMap.forEach((x, y) -> propertyMap.put(x, loadProperties(y)));
        LOGGER.info(PROP_INIT_OK);
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
            LOGGER.error(PROP_INIT_FAIL, path, e);
        }
        return property;
    }
}