import com.srajgopalan.camel.route.RestGetRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class RestGetRouteTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RestGetRoute();
    }

    @Test
    public void checkRestGetRoute(){
        String input = "IND";

        String output = template.requestBody("direct:restInput",input,String.class);

        System.out.println("Response:"+output);
        assertNotNull(output);


    }
}
