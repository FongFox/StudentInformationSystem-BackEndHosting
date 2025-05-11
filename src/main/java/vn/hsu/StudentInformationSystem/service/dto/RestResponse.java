package vn.hsu.StudentInformationSystem.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {
    private int status; //status code

    private Object message; // success message

    private Object error; // error detail
    private T Data; // success data

    public RestResponse() {
    }
}
