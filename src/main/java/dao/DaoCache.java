package dao;

import manager.DBManager;
import manager.DataSourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

import static utility.ClassNameUtil.getClassName;

public class DaoCache {

    private static DaoCache ourInstance = new DaoCache();

    private static final Logger LOG = LogManager.getLogger(getClassName());

    /** DaoSpreader that every IDao implementation has as constructor param */
    private DaoSpreader defaultDaoSpreader = new DaoSpreader(DBManager.getInstance());
    /** Saves already initialized dao's to prevent continuous initialization */
    private HashSet<IDao> daoCache = new HashSet<>();

    public static DaoCache getCache() { return ourInstance; }

    private DaoCache() { }

    /**
     * Changes default DaoSpreader with DataSourceManager as constructor param
     * @param dataSourceManager interface, which implementations points on
     *                          what data storage should be used
     */
    public void changeDefault(DataSourceManager dataSourceManager){
        this.defaultDaoSpreader = new DaoSpreader(dataSourceManager);
    }

    /**
     * Searches for dao's in cache, if not present - returns new
     * @param className name of what IDao implementation is needed
     * @return new DaoSpreader instance of specified type if no exception was thrown
     *         else - null
     */
    public IDao getDao(String className){
        //search in cache
        for (IDao current: daoCache){
            if (current.getClass().getName().equals(className)) return current;
        }
        //if not found in cache
        return initAndSave(className);
    }

    @SuppressWarnings("unchecked")
    private IDao initAndSave(String className){
        IDao newDao = null;
        try {
            Class neededClass = Class.forName(className);
            newDao = (IDao) neededClass.getConstructor(DaoSpreader.class)
                                       .newInstance(defaultDaoSpreader);
            daoCache.add(newDao);
        } catch (Exception e) {
            LOG.warn("Initialization of IDao implementaion {} failure", className, e);
        }
        return newDao;
    }
}
