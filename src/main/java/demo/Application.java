package demo;

import demo.adapter.MyTableBeanPropertyStructMapper;
import demo.model.MyTable;
import oracle.jdbc.OracleTypes;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.data.jdbc.support.oracle.SqlReturnStructArray;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.arrayToList;

@SpringBootApplication
public class Application {

    private static final ObjectWriter OBJECT_WRITER = new ObjectMapper()
            .writerWithDefaultPrettyPrinter();
    private static final Integer[] INTEGER_ARRAY = {
            1, 2, 3
    };

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        SimpleJdbcCall call = context.getBean(SimpleJdbcCall.class);
        try {
            Map<String, Object> outParameters = call.execute(0, new SqlArrayValue<>(INTEGER_ARRAY));
            System.out.println("call.execute() = " + toJson(outParameters));

            // CollectionUtils.arrayToList works properly for all cases.
            List<MyTable> myTableValueList = arrayToList(outParameters.get("P_ARG3"));
            System.out.println("myTableValueList = " + toJson(myTableValueList)); //

            // I recommend managing a errorMessage variable in the stored procedure for the cases
            // in which a partial success is posible. (When operating on multiple rows for example)
            String errMessage = (String) outParameters.get("V_ERRM");
            System.out.println("errMessage = " + errMessage);
        } catch (DataAccessException e) {
            // If you raise an exception in the Sproc. You can catch it here.
            e.printStackTrace();
            // Also receives custom exceptions. (Their class is org.springframework.jdbc.UncategorizedSQLException)
            // Throw them with raise_application_error( -20001, 'This is a custom error' );
        }

        context.close();
    }

    private static String toJson(Object o) throws IOException {
        return OBJECT_WRITER.writeValueAsString(o);
    }

    @Bean
    public SimpleJdbcCall simpleJdbcCall(DataSource dataSource, Environment environment) throws SQLException {
        return new SimpleJdbcCall(dataSource)
                .withSchemaName(environment.getProperty("spring.datasource.username"))
                .withCatalogName("MY_PACKAGE")
                .withProcedureName("TEST")
                .declareParameters(
                        new SqlParameter("P_ARG1", Types.NUMERIC),
                        new SqlParameter("P_ARG2", OracleTypes.ARRAY, "MY_NARRAY"),
                        new SqlOutParameter("P_ARG3", OracleTypes.ARRAY, "MY_ARRAY",
                                new SqlReturnStructArray<>(
                                        new MyTableBeanPropertyStructMapper())),
                        new SqlOutParameter("P_ERRM", Types.VARCHAR));
    }

}
