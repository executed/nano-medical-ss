package manager;

import org.apache.logging.log4j.Logger;
import service.PropertiesService;

import java.sql.*;

import static org.apache.logging.log4j.LogManager.getLogger;
import static utility.ClassNameUtil.getClassName;

public class DBManager implements DataSourceManager<Connection>{

    private static final Logger LOGGER = getLogger(getClassName());
    private static DBManager ourInstance = new DBManager();

    private Connection connection;

    private DBManager() {
        initializeConnection();
    }

    public static DBManager getInstance() { return ourInstance; }

    public Connection getConnection() { return  this.connection; }

    public void initializeConnection() {
        LOGGER.info("Database initialization started");
        PropertiesService properties = new PropertiesService();

        try {
            Class.forName(properties.getPropertyByKey("db.classname"));
            String url = properties.getPropertyByKey("db.url");
            String login = properties.getPropertyByKey("db.login");
            String password = properties.getPropertyByKey("db.password");
            connection = DriverManager.getConnection(url, login, password);
            LOGGER.info("Database started with params: "+url+", "+login+", "+password);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.fatal("Connection to database wasn't established properly", e);
            System.exit(1);
        }
    }
}
