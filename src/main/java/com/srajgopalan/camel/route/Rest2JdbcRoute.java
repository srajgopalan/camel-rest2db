package com.srajgopalan.camel.route;

import com.srajgopalan.camel.process.SimpleJDBCInsertProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class Rest2JdbcRoute extends RouteBuilder {

    public void configure() throws Exception {
        from("timer:timerInput?period=10s")
                .to("log:?level=INFO&showHeaders=true&showBody=true")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_URI,simple("http://restcountries.eu/rest/v2/alpha/us"))
                .to("http://restcountries.eu/rest/v2/alpha/us").convertBodyTo(String.class)
                .to("log:?level=INFO&showBody=true")
                .process(new SimpleJDBCInsertProcessor())
                .to("jdbc:myDataSource")
                .to("sql:select * from country_capital?dataSource=myDataSource")
                .to("log:?level=INFO&showBody=true")
                .to("direct:dbOutput"); // this is so that we can etract the output of the SQL statement to test against. Will be commented when running as a standalone app


    }
}
