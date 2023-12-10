
package api;

import jakarta.inject.Singleton;
import model.RequestDto;
import model.ResponseDto;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rest.RestConstants;
import org.apache.camel.model.rest.RestBindingMode;
import org.jboss.logging.Logger;
import processor.PrepareResponseProcessor;

import java.io.ByteArrayOutputStream;

import static io.quarkus.logging.Log.log;

@Singleton
public class RestAPI extends RouteBuilder {
    //@Inject

    private static final String GENERATE_RESPONSE_ENDPOINT = "direct:generateResponseEndpoint";
    private static final String APPLICATION_JSON = "application/json";

    ByteArrayOutputStream errorStream = new ByteArrayOutputStream();


    @Override
    public void configure() {

        //getContext().getPropertiesComponent().setLocation("C:\\Users\\Lenovo\\Desktop\\apache-camel-quarkus\\src\\main\\resources\\application.yaml");

        log(Logger.Level.INFO,"inside configuration...");
        /*this.onCompletion();
        this.intercept();*/
        //this.getCamelContext().setTracing(true);
        //getCamelContext().setBacklogTracing(true);

        this.onException()
                .handled(true)
                .log(LoggingLevel.ERROR,"An Exception Occurred During Rest API")
                        .log(LoggingLevel.ERROR,"${"+"exchangeProperty."+Exchange.EXCEPTION_CAUGHT+"}"); // print the exception

        this.onCompletion()
                .log("The Integration is completed.");



        //this.getCamelContext().setNameStrategy();
        //this.onException();


        restConfiguration().host("localhost").port(8080)/*.producerComponent("http")*/.bindingMode(RestBindingMode.json);

        rest("return")

                //.path("")
                .post("/employee-info")
                .routeId("ExampleRestAPIinCamelRestId")
                .description("REST API description: Configuring Apache Camel over Quarkus")
                .consumes(APPLICATION_JSON) // the API excepts JSON format as request
                .produces(APPLICATION_JSON) // the API returns JSON format as response
                .type(RequestDto.class) // enforce requests to a specific type
                .outType(ResponseDto.class) // always return the specific type if no error occurred
                .to(GENERATE_RESPONSE_ENDPOINT);

        from(GENERATE_RESPONSE_ENDPOINT)
                .routeId("ExampleApacheCamelRestRouteId")
                .streamCaching()
                //.log(LoggingLevel.INFO,"Loaded Properties: {}", String.valueOf(simple(String.valueOf(getContext().getPropertiesComponent().loadProperties()))))
                .removeHeaders("*")
                .setExchangePattern(ExchangePattern.InOut) // default exchange pattern is already InOut
                .setProperty("stream", constant(errorStream))
                //.log("{{active.mod}}")
                //.split()
                //.aggregate()
                //.receipentList()
                //.bean("")
                //routeId()
                //.doTry()
                //.enrich()
                //.validate()
                //.filter()
                //.setHeader(RestConstants.HTTP_METHOD)
               /* .unmarshal()
                .json()*/
                .process(new PrepareResponseProcessor())
/*                .process(e ->{
                    throw new Exception("error occurred");
                })*/
                .log(LoggingLevel.INFO,"response is created...")
               .log("{{active.mode}}")
                .setHeader(Exchange.CONTENT_TYPE, constant(APPLICATION_JSON))
                .setHeader(RestConstants.HTTP_RESPONSE_CODE,constant(200))
                .end();

/*  .transform()
                .wireTap()*//*

*/
/*                .loopDoWhile()
                .rollback()
                .pipeline()*/

                //.multicast()
                //.aggregationStrategy()
                //.process()

    }
}

