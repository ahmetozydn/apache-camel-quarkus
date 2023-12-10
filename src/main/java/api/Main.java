package api;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class Main {
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

        camelContext.addRoutes(new RestAPI());

        camelContext.start();

        Thread.sleep(30000);

        // Stop the Camel context
        camelContext.stop();
    }
}
