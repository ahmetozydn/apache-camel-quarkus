package model;

public class ResponseDto {
    private int status;

    private String errorMessage;

    EmployeeInfoResponseDto employeeInfoResponseDto;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public EmployeeInfoResponseDto getEmployeeInfoResponseDto() {
        return employeeInfoResponseDto;
    }

    public void setEmployeeInfoResponseDto(EmployeeInfoResponseDto employeeInfoResponseDto) {
        this.employeeInfoResponseDto = employeeInfoResponseDto;
    }


}
