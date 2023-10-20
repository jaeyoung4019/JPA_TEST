package toy.project.local_specialty.local_famous_goods.utils.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ErrorUtils {
    /**
     * 에러 처리
     * @param code
     * @throws IOException
     */
    public void error(ErrorCode code) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();

        assert response != null;
        response.sendRedirect(request.getContextPath() + "/error/" + code.getValue());
    }
}
