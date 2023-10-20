package toy.project.local_specialty.local_famous_goods.domain.auth;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import toy.project.local_specialty.local_famous_goods.dto.enums.AuthoritiesEnum;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity
@ToString
public class Authority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "mebmer_idx")
    private Member member;
    @Enumerated(EnumType.STRING)
    private AuthoritiesEnum auth;

    private boolean enable;

    @Builder
    private Authority(Member member, AuthoritiesEnum auth , boolean isEnable) {
        this.member = member;
        this.auth = auth;
        this.enable = isEnable;
    }

    public static Authority createAuthorityMember(Member member , AuthoritiesEnum auth ){
        return Authority.builder()
                    .member(member)
                    .auth(auth)
                    .isEnable(true)
                    .build();
    }
}
