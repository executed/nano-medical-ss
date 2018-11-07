package manager;

import java.sql.SQLException;

public interface DataSourceManager<T>{

    void initializeConnection() throws Exception;

    T getConnection();
}
