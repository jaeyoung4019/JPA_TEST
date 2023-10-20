package toy.project.local_specialty.local_famous_goods.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.project.local_specialty.local_famous_goods.dto.login.LoginByMemberRequest;
import toy.project.local_specialty.local_famous_goods.dto.login.LoginByMemberResponse;
import toy.project.local_specialty.local_famous_goods.dto.response.Response;
import toy.project.local_specialty.local_famous_goods.exception.RestException;
import toy.project.local_specialty.local_famous_goods.service.auth_service.AuthService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public Response<LoginByMemberResponse> loginByMember(@RequestBody @Valid LoginByMemberRequest param , BindingResult bindingResult) throws Exception{
        if(bindingResult.hasErrors()) {
           throw new RestException(403 , "알맞은 값을 입력해주세요.");
        }
        return new Response.ResponseBuilder<LoginByMemberResponse>("로그인 성공" , 200)
                .resultResponse(authService.loginByMember(param))
                .total(1).build();
    }

    //    @PostMapping("/sale_member/login")
//    public Response<AuthMemberResponse> authorityMember(@RequestBody AuthSaleMemberRequest authMemberRequest) throws Exception {
//        AtomicReference<AuthMemberResponse> authMemberResponseAtomicReference = authService.authLogin(authMemberRequest);
//        AuthMemberResponse authMemberResponse =  authMemberResponseAtomicReference.get();
//
//        if(authMemberResponse.getMemberIdx() != 0L) {
//            String token = jwtUtil.generateToken(authMemberResponse.getMemberIdx(), "SALE");
//            authMemberResponse.setToken(token);
//            return new Response.ResponseBuilder<AuthMemberResponse>("로그인 성공" , 200)
//                    .resultResponse(authMemberResponse)
//                    .total(1)
//                    .build();
//        } else {
//            throw new RestException(404 ,"존재하지 않는 회원입니다");
//        }
//    }

//    @PostMapping("/login")
//    private Response<LoginByMemberResponse> loginByMember(@RequestBody LoginByMemberRequest){
//
//    }
}
