# Running an Oracle Stored-Procedure that returns Struct Array and receives Array   


### How to execute 
 1. Download and run [Oracle express](http://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html).
 1. Setup [Oracle's repository](http://docs.oracle.com/middleware/1213/core/MAVEN/config_maven_repo.htm#MAVEN9016)
 1. Run `mvn spring-boot:run`
 
### Note

Using C3P0 will require to subclass  a `org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor` when instantiating
  
  ```
    public class NativeConnectionAwareArrayValue<T> extends SqlArrayValue<T> {

        public NativeConnectionAwareArrayValue(T[] arrayValue) {
            super(arrayValue);
        }

        @Override
        protected Object createTypeValue(Connection conn, int sqlType, String typeName) throws SQLException {
            Connection nativeConnection = new C3P0NativeJdbcExtractor().getNativeConnection(conn);
            return super.createTypeValue(nativeConnection, sqlType, typeName);
        }
    }
  ```
 
### Sources
http://markchensblog.blogspot.mx/2015/03/use-spring-simplejdbccall-to-invoke.html
http://docs.spring.io/spring-data/jdbc/docs/current/reference/html/orcl.datatypes.html

http://stackoverflow.com/questions/6020450/oracle-pl-sql-raise-user-defined-exception-with-custom-sqlerrm
http://www.oracle.com/technetwork/issue-archive/2008/08-mar/o28plsql-095155.html
http://www.oracle.com/technetwork/issue-archive/2012/12-sep/o52plsql-1709862.html

http://stackoverflow.com/questions/15292331/sql-state-99999-error-code-17004-invalid-column-type-1111-with-spring-sim
http://www.dba-oracle.com/sf_ora_00947_not_enough_values.htm
