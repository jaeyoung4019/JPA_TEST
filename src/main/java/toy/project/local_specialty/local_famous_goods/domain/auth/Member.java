package toy.project.local_specialty.local_famous_goods.domain.auth;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import toy.project.local_specialty.local_famous_goods.domain.base_entity.BaseTimeEntity;
import toy.project.local_specialty.local_famous_goods.domain.embedded.Address;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "member" , uniqueConstraints = @UniqueConstraint(columnNames = "userId"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="member_type")
public abstract class Member extends BaseTimeEntity implements Serializable {

    @Id
    @Column(name = "member_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String userId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    private LocalDateTime lastLoginDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city" , column = @Column(name = "city" , nullable = false)), // 도시
            @AttributeOverride(name = "street" , column = @Column(name = "street" , nullable = false)), // 상세주소
            @AttributeOverride(name = "zipCode" , column = @Column(name = "zip_code" , nullable = false)) // 우편번호
    })
    private Address address;

    abstract static class Builder<T extends Builder<T>> {
        private String userId;

        private String password;
        private String name;
        private Address address;

        public T address(Address value){
            this.address = value;
            return self();
        }

        public T userId(String value){
            this.userId = value;
            return self();
        }

        public T password(String value){
            this.password = value;
            return self();
        }

        public T name(String value){
            this.name = value;
            return self();
        }

        abstract Member build();

        protected abstract T self();
    }

    Member(Builder<?> builder) {
        userId = builder.userId;
        password = builder.password;
        name = builder.name;
        address = builder.address;
    }


    public void memberSecuritySetPassword(String password){
        this.password = password;
    }
}
