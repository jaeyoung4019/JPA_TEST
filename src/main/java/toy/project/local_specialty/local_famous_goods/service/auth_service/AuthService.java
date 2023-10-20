package toy.project.local_specialty.local_famous_goods.service.auth_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.project.local_specialty.local_famous_goods.dto.authority.MemberDetails;
import toy.project.local_specialty.local_famous_goods.dto.login.LoginByMemberRequest;
import toy.project.local_specialty.local_famous_goods.dto.login.LoginByMemberResponse;
import toy.project.local_specialty.local_famous_goods.repository.AuthorityRepository;
import toy.project.local_specialty.local_famous_goods.repository.MemberRepository;
import toy.project.local_specialty.local_famous_goods.utils.auth.JwtUtil;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthorityRepository authorityRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authorityRepository
                .findByMember_UserId(username)
                .map( authority -> MemberDetails.createMemberDetails( authority.getMember() , authority))
                .orElseThrow( () -> new UsernameNotFoundException("유저 정보를 찾을 수 없습니다."));
    }

    @Transactional
    public LoginByMemberResponse loginByMember(LoginByMemberRequest loginByMemberRequest){
        return authorityRepository.findByMember_UserId(loginByMemberRequest.getUserId())
                .filter(authority -> bCryptPasswordEncoder
                        .matches(loginByMemberRequest.getPassword() ,
                                    authority.getMember().getPassword()))
                .map(authority -> {
                    String token = jwtUtil.generateToken(authority.getMember().getId()
                                                            , authority.getAuth().getPaid());
                    updateLoginDate(authority.getId());
                    return LoginByMemberResponse.createLoginMemberResponse(authority , token);
                })
                .orElseThrow( () -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

    }

    @Transactional(readOnly = true)
    public MemberDetails findByMemberDetails(Long id){
        return authorityRepository.findById(id)
                .map(authority -> MemberDetails.createMemberDetails(authority.getMember() , authority))
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private void updateLoginDate(Long id){
        LocalDateTime localDateTime = LocalDateTime.now();
        memberRepository.updateLoginDate(id, localDateTime);
    }


//    public AtomicReference<AuthMemberResponse> authLogin(AuthSaleMemberRequest authMemberRequest) {
//        String password = authMemberRequest.getPassword();
//        AtomicReference<AuthMemberResponse> authMemberResponse = new AtomicReference<>();
//
//        memberRepository.findIdAndPasswordById(authMemberRequest.getId()).ifPresent(
//            matcher -> {
//                String beforePassword = matcher.getPassword();
//                boolean passwordMatches = bCryptPasswordEncoder.matches(password, beforePassword);
//                if(passwordMatches) {
//                    Optional<AuthMemberResponse> result= authorityRepository.findMemberById(authMemberRequest.getId())
//                            .map(AuthMemberResponse::createAuthMemberRes);
//                    result.ifPresent(authMemberResponse::set);
//                    // ifPresent 안이라서 리턴을 줄수가 없음. 그래서 아토믹레퍼런스로 동시성을 보장하게 끔 빈 생성자를 만들게 되는데,
//                    // 해결 방안 1 . 비밀번호 매칭 메소드를 private 메소드로 생성해서 return 값을 boolean 으로 받아서
//                    //              처리하는 방법.
//                    // 해결 방안 2 . 아토믹 그대로 쓰기
//                    // 해결 방안 3 . where 절에 바로 패스워드와 비밀번호를 둘다 걸어서 처리하는 방법  이게 재일 나으려나,
//                }
//            }
//        );
//
//        return authMemberResponse;
//    }
//    public Optional<AuthMemberResponse> authLogin(AuthSaleMemberRequest authMemberRequest) {
////        Optional<Authority> memberByIdAndPassword = authorityRepository.findMemberByIdAndPassword(authMemberRequest.getId(), bCryptPasswordEncoder.encode(authMemberRequest.getPassword()));
////
//       return null;
//    }


}
