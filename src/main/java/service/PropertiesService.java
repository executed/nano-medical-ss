package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import static constant.STR_CONSTANT.PATH_PROPERTIES;
import static utility.ClassNameUtil.getClassName;
//TODO: Make class singleton with different Property types
public class PropertiesService {

    private String configFilePath = PATH_PROPERTIES;
    private Properties property;
    private static Logger LOGGER = LogManager.getLogger(getClassName());

    public PropertiesService(){
        loadProperties();
    }

    public String getPropertyByKey(String key){ return property.getProperty(key); }

    public String getPropertyByKey(String key, String configFilePath){

        this.configFilePath = configFilePath;
        loadProperties();
        String keyValue = null;
        try {
            keyValue = new String(property.getProperty(key).getBytes("ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("Property wasn't loaded", e);
        }
        this.configFilePath = PATH_PROPERTIES;
        return keyValue;
    }

    private void loadProperties(){
        if (property == null) property = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(configFilePath);
            property.load(inputStream);
        }
        catch (IOException e) {
            LOGGER.error("Property file wasn't loaded", e);
        }
    }
}
