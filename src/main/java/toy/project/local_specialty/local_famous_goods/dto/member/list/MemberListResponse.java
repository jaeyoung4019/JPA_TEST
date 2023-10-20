package toy.project.local_specialty.local_famous_goods.dto.member.list;

import lombok.Data;
import toy.project.local_specialty.local_famous_goods.domain.embedded.Address;

import java.time.LocalDateTime;
@Data
public abstract class MemberListResponse {

    private String userId;

    private String name;

    private LocalDateTime lastLoginDate;

    private Address address;
}
