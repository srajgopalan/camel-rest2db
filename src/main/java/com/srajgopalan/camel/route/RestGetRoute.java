package com.srajgopalan.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.cookie.CookieHandler;
import org.apache.camel.http.common.cookie.InstanceCookieHandler;

public class RestGetRoute extends RouteBuilder {
    public void configure() throws Exception {

        CookieHandler cookieHandler = new InstanceCookieHandler();

        from("direct:restInput")
                .to("log:?level=INFO&showBody=true")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader(Exchange.HTTP_URI, simple("http://restcountries.eu/rest/v2/alpha/${body}"))
                .to("log:?level=INFO&showBody=true&showHeaders=true")
                .to("http://restcountries.eu/rest/v2/alpha/${body}")
                .to("log:?level=INFO&showBody=true");



    }
}
