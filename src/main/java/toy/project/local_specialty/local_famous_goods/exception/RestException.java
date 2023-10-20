package toy.project.local_specialty.local_famous_goods.exception;

import lombok.Getter;
import toy.project.local_specialty.local_famous_goods.dto.response.Response;

@Getter
public class RestException extends Exception{

    private final Response response;

    public RestException(int code, String message) {
        response = new Response.ResponseBuilder<>(message ,code).build();
    }
}
