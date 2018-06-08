import com.srajgopalan.camel.route.JdbcInsertRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcInsertRouteTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new JdbcInsertRoute();
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
    public void checkJDBCInsert() {
        String input = "{\"name\":\"India\",\"topLevelDomain\":[\".in\"],\"alpha2Code\":\"IN\",\"alpha3Code\":\"IND\",\"callingCodes\":[\"91\"],\"capital\":\"New Delhi\",\"altSpellings\":[\"IN\",\"Bhārat\",\"Republic of India\",\"Bharat Ganrajya\"],\"region\":\"Asia\",\"subregion\":\"Southern Asia\",\"population\":1295210000,\"latlng\":[20.0,77.0],\"demonym\":\"Indian\",\"area\":3287590.0,\"gini\":33.4,\"timezones\":[\"UTC+05:30\"],\"borders\":[\"AFG\",\"BGD\",\"BTN\",\"MMR\",\"CHN\",\"NPL\",\"PAK\",\"LKA\"],\"nativeName\":\"भारत\",\"numericCode\":\"356\",\"currencies\":[{\"code\":\"INR\",\"name\":\"Indian rupee\",\"symbol\":\"₹\"}],\"languages\":[{\"iso639_1\":\"hi\",\"iso639_2\":\"hin\",\"name\":\"Hindi\",\"nativeName\":\"हिन्दी\"},{\"iso639_1\":\"en\",\"iso639_2\":\"eng\",\"name\":\"English\",\"nativeName\":\"English\"}],\"translations\":{\"de\":\"Indien\",\"es\":\"India\",\"fr\":\"Inde\",\"ja\":\"インド\",\"it\":\"India\",\"br\":\"Índia\",\"pt\":\"Índia\",\"nl\":\"India\",\"hr\":\"Indija\",\"fa\":\"هند\"},\"flag\":\"https://restcountries.eu/data/ind.svg\",\"regionalBlocs\":[{\"acronym\":\"SAARC\",\"name\":\"South Asian Association for Regional Cooperation\",\"otherAcronyms\":[],\"otherNames\":[]}],\"cioc\":\"IND\"}";

        List response = template.requestBody("direct:jdbcInput",input,ArrayList.class);

        for(int i=0;i<response.size();i++){
            System.out.println(response.get(i));
        }

        assertNotEquals(0,response.size());

    }

}
