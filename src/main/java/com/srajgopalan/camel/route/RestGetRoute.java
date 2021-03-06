package com.srajgopalan.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class RestGetRoute extends RouteBuilder {
    public void configure() throws Exception {

        from("direct:restInput")
                .to("log:?level=INFO&showBody=true")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_URI, simple("http://restcountries.eu/rest/v2/alpha/${body}"))
                .to("log:?level=INFO&showBody=true&showHeaders=true")
                .to("http://restcountries.eu/rest/v2/alpha/${body}")
                .to("log:?level=INFO&showBody=true");



    }
}
