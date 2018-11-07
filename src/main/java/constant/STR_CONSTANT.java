package constant;

public final class STR_CONSTANT implements IConstant{
    //path
    public static final String PATH_PROPERTIES = "src/main/resources/properties/configuration.properties";
    public static final String PATH_SQL_QUERIES = "src/main/resources/sql/sql_queries.properties";

    //time
    public static final String FORMATTER_TIME_PATTERN_DEF = "dd/MM/yyyy HH:mm";

    //exceptions
    public static final String NO_OVERLAP_EXC = "TimeSlot doesn't overlap any of the set";
    public static final String DOC_WORKTIME_EXC = "Doctor doesn't work at this time";
}
