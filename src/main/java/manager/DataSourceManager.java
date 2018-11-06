package manager;

public interface DataSourceManager<T>{

    void initializeConnection();

    T getConnection();
}
