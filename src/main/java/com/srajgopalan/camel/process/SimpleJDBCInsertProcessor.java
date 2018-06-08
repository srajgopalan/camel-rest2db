package com.srajgopalan.camel.process;

import org.apache.camel.Exchange;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SimpleJDBCInsertProcessor implements org.apache.camel.Processor {

    private  static int count = 0;

    public void process(Exchange exchange) throws Exception {

        count ++;

        String input = (String) exchange.getIn().getBody();
        System.out.println("Input to be persisted : " + input);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(input);

        JSONObject jsonObject = (JSONObject) obj;

        String name = (String) jsonObject.get("name");

        String capital = (String) jsonObject.get("capital");

        String insertQuery = "INSERT INTO country_capital values ( ".concat("'").concat(name).concat("'").concat(",").concat("'").concat(capital).concat("'").concat(" )");
        System.out.println("Insert Query is : " + insertQuery);
        exchange.getIn().setBody(insertQuery);
    }
}
