package com.srajgopalan.camel.exception;

import org.apache.camel.Exchange;

public class SimpleExceptionhandlerProcessor implements org.apache.camel.Processor {
    public void process(Exchange exchange) throws Exception {

        //Simply logging the exception. In reality you may want to do more, eg
        //publish to a topic or send an alert

        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        System.out.println("Encountered Exception in Route:\nException Message:"  + e.getMessage());
        System.out.println("Exception Class:"    + e.getClass());
        String failedEndpoint = (String) exchange.getProperty(Exchange.FAILURE_ENDPOINT);
        System.out.println("Failed Endpoint: "+failedEndpoint);

        exchange.getIn().setBody("Exception caught on route!");
    }
}
