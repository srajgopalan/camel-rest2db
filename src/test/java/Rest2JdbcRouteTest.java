import com.srajgopalan.camel.route.Rest2JdbcRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;

public class Rest2JdbcRouteTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new Rest2JdbcRoute();
    }

    @Override
    public CamelContext createCamelContext() throws Exception {
        //this is where we will create the datasource
        String url = "jdbc:postgresql://localhost:5432/localdb";
        DataSource dataSource = setupDataSource(url);

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myDataSource",dataSource);

        return new DefaultCamelContext(registry);
    }

    private static DataSource setupDataSource(String url){
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("srajgopalan");
        ds.setPassword("srajgopalan");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(url);

        System.out.println( "returning datasource");

        return ds;
    }

    @Test
    public void checkRest2Jdbc(){

        ArrayList responseList  = consumer.receiveBody("direct:dbOutput",ArrayList.class);

        System.out.println("got the response list");

        System.out.println("Response contains "+ responseList.size() + " elements");

        assertNotEquals(0,responseList.size());

    }

    @Test
    public void checkRest2JdbcException(){

        // Exception will be thrown back to the caller timer class so we will get some output here
        ArrayList responseList  = consumer.receiveBody("timer:timerInput",ArrayList.class);

        assertNull(responseList);

    }
}
