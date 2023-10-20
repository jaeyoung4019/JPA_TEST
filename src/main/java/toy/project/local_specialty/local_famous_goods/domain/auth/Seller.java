package toy.project.local_specialty.local_famous_goods.domain.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.project.local_specialty.local_famous_goods.domain.converter.AES256Convertor;
import toy.project.local_specialty.local_famous_goods.domain.embedded.Address;
import toy.project.local_specialty.local_famous_goods.dto.enums.PaymentGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@DiscriminatorValue("seller")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@PrimaryKeyJoinColumn(name = "seller_id")
@Getter
public class Seller extends Member {
    @Column(nullable = false)
    private String brandName;
    @Convert(converter = AES256Convertor.class)
    @Column(nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private String bankName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city" , column = @Column(name = "company_city" , nullable = false)), // 도시
            @AttributeOverride(name = "street" , column = @Column(name = "company_street" , nullable = false)), // 상세주소
            @AttributeOverride(name = "zipCode" , column = @Column(name = "company_zip_code" , nullable = false)) // 우편번호
    })
    private Address companyAddress;
    @Column(nullable = false)
    private String category;

    private Seller(Builder builder){
        super(builder);
        brandName = builder.brandName;
        accountNumber = builder.accountNumber;
        bankName = builder.bankName;
        companyAddress = builder.companyAddress;
        category = builder.category;
    }

    public static class Builder extends Member.Builder<Builder> {

        private final String brandName;
        private final String accountNumber;
        private final String bankName;
        private final Address companyAddress;
        private final String category;
        public Builder(String brandName, String accountNumber, String bankName , Address companyAddress , String category) {
            this.brandName = brandName;
            this.accountNumber = accountNumber;
            this.bankName = bankName;
            this.companyAddress = companyAddress;
            this.category = category;
        }

        @Override
        public Seller build() {
            return new Seller(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
