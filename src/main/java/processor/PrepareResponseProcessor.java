package processor;

import io.quarkus.logging.Log;
import model.EmployeeInfoResponseDto;
import model.RequestDto;
import model.ResponseDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.eclipse.microprofile.config.ConfigProvider;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrepareResponseProcessor implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(errorStream));
        Log.info("logging");
        System.err.println("Error.");
        String databaseName = ConfigProvider.getConfig().getValue("active.mode", String.class);
        System.err.println("db "+databaseName);
        RequestDto requestDto = exchange.getIn().getBody(RequestDto.class);
        ResponseDto responseDto = prepareServiceResponse(requestDto);
        exchange.getIn().setBody(responseDto);
        //System.err.println("Error...");
        String errorMessages = errorStream.toString();


        // Check for error-level messages
        if (errorMessages.contains("ERROR") || errorMessages.contains("Exception")) {
            System.out.println("Error-level messages found: " + errorMessages);
            // Take appropriate actions based on the error conditions
        } else {
            System.out.println("No error-level messages found");
        }

    }

    public ResponseDto prepareServiceResponse(RequestDto requestDto) {
        ResponseDto responseDto = new ResponseDto();
        EmployeeInfoResponseDto employeeInfoResponseDto = new EmployeeInfoResponseDto();

        employeeInfoResponseDto.setName("JOHN");
        employeeInfoResponseDto.setEmployeeId(requestDto.getEmployeeId());
        employeeInfoResponseDto.setSurname("PETER");

        responseDto.setStatus(200);
        responseDto.setErrorMessage(null);
        responseDto.setEmployeeInfoResponseDto(employeeInfoResponseDto);

        return responseDto;
    }
}
