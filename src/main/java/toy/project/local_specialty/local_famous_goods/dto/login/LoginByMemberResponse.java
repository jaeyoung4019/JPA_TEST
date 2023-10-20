package toy.project.local_specialty.local_famous_goods.dto.login;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import toy.project.local_specialty.local_famous_goods.domain.auth.Authority;
import toy.project.local_specialty.local_famous_goods.dto.enums.AuthoritiesEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginByMemberResponse {

    private long memberIdx;
    private final AuthoritiesEnum auth;
    private List<GrantedAuthority> grantedAuth;
    private final String token;
    private final String name;

    private LoginByMemberResponse(AuthoritiesEnum auth,  String name , String token) {
        this.auth = auth;
        this.name = name;
        setGrantedAuth(auth);
        this.token = token;
    }

    public static LoginByMemberResponse createLoginMemberResponse(Authority authority , String token) {
        return new LoginByMemberResponse(
                      authority.getAuth(),
                      authority.getMember().getName(),
                      token
                    );
    }

    private void setGrantedAuth(AuthoritiesEnum grantedAuth) {
        this.grantedAuth = Arrays.stream(grantedAuth.getOptions())
                            .map(authorityOptionEnum -> new SimpleGrantedAuthority(authorityOptionEnum.getOptionName()))
                            .collect(Collectors.toList());
    }

}
