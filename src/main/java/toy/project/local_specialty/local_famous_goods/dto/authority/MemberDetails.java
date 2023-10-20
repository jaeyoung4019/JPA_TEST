package toy.project.local_specialty.local_famous_goods.dto.authority;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import toy.project.local_specialty.local_famous_goods.domain.auth.Authority;
import toy.project.local_specialty.local_famous_goods.domain.auth.Member;
import toy.project.local_specialty.local_famous_goods.dto.enums.AuthoritiesEnum;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class MemberDetails implements UserDetails  {

    private final long id;
    private final Member member;
    private final String username;
    private final List<GrantedAuthority> authorities;
    private final Authority authority;

    @Builder
    private MemberDetails(Member member , Authority authority){
        this.id = member.getId();
        this.member = member;
        this.username = member.getUserId();
        this.authorities = Arrays.stream(authority.getAuth().getOptions())
                .map(authorityOptionEnum -> new SimpleGrantedAuthority(authorityOptionEnum.getOptionName()))
                .collect(Collectors.toList());
        this.authority = authority;
    }

    public static MemberDetails createMemberDetails(Member member , Authority authority){
        return MemberDetails.builder().member(member).authority(authority).build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return authority.isEnable();
    }
}
