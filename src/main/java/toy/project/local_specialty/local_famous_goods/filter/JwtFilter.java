package toy.project.local_specialty.local_famous_goods.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import toy.project.local_specialty.local_famous_goods.dto.authority.MemberDetails;
import toy.project.local_specialty.local_famous_goods.service.auth_service.AuthService;
import toy.project.local_specialty.local_famous_goods.utils.auth.JwtUtil;
import toy.project.local_specialty.local_famous_goods.utils.auth.URLMatcher;
import toy.project.local_specialty.local_famous_goods.utils.error.ErrorCode;
import toy.project.local_specialty.local_famous_goods.utils.error.ErrorUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class JwtFilter extends OncePerRequestFilter {

    @Value("${spring.jwt.header}")
    private String SPRING_JWT_HEADER;

    private JwtUtil jwtUtil;

    private ErrorUtils errorUtils;

    private AuthService authService;

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setErrorUtils(ErrorUtils errorUtils) {
        this.errorUtils = errorUtils;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 크롬 OPTIONS 요청 처리
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        URLMatcher indexMatcher = new URLMatcher(List.of("/login"));
        if (indexMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = request.getHeader(SPRING_JWT_HEADER);
        URLMatcher admMatcher = new URLMatcher(List.of("/cms/**"));   // 관리자
        if (admMatcher.matches(request)) {

            if(token == null){
                System.out.println("token missing");
              errorUtils.error(ErrorCode.UNAUTHORIZED);
              return;
            }

            try {
                if (!jwtUtil.validateToken(token)) {
                    errorUtils.error(ErrorCode.EXPIRED_TOKEN);
                    return;
                }
            } catch (Exception e) {
                errorUtils.error(ErrorCode.INVALID_TOKEN);
                return;
            }

        }


        if (token != null) {
            // 관리자 AuthController 에서 받아서 처리할 때 "A" 를 타이프로 주면 어드민 인거 체크함.
//            if ("SELLER".equals(jwtUtil.getType(token))) {
                // 관리자 정보 조회
            MemberDetails memberDetails = authService.findByMemberDetails(jwtUtil.getMemberId(token));

            if (memberDetails == null) {
                errorUtils.error(ErrorCode.NOT_FOUND_USER);
                return;
            }
            System.out.println("필터탐 ");
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( memberDetails, null,  memberDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
        }

        filterChain.doFilter(request, response);
    }
}
