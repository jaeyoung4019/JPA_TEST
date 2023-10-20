package toy.project.local_specialty.local_famous_goods.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toy.project.local_specialty.local_famous_goods.dto.response.Response;

@Slf4j
@RestControllerAdvice
public class RestExceptionAdvisor {

    @ExceptionHandler(RestException.class) @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public Response handleRESTException(RestException e) {
        return e.getResponse();
    }

    @ExceptionHandler(BindException.class) @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public Response validationBindException(BindException e) {
        return new Response.ResponseBuilder<>("알맞은 형식의 값을 입력해주세요." , 403).build();
    }
    @ExceptionHandler(UsernameNotFoundException.class) @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public Response userNameNotFoundException(UsernameNotFoundException e){
        return new Response.ResponseBuilder<>("아이디나 비밀번호가 맞지 않습니다." , 403).build();
    }
}
