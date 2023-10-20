package toy.project.local_specialty.local_famous_goods.dto.member.save;

import lombok.*;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellerSaveRequest {

    @Pattern(regexp = "^[a-zA-Z0-9]*$" , message = "영어 , 숫자 조합으로 아이디를 입력해주세요.")
    private String userId;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[A-Za-z\\d]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[a-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$"
            , message = "특수문자, 소문자 , 대문자 , 숫자중 3가지를 포함, 9글자 이상 20글자 이하로 비밀번호를 작성해주세요")
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String brandName;
    @NotNull
    private String bankName;
    @Pattern(regexp = "^[0-9]*$" , message = "숫자만 입력해 주세요.")
    private String acountNumber;
    @NotNull
    private String city;
    @NotNull
    private String street;
    @NotNull
    private String zipCode;
    @NotNull
    private String companyCity;
    @NotNull
    private String companyStreet;
    @NotNull
    private String companyZipCode;
    @NotNull
    private String category;

    @Transient
    public String testReflection;
    // TEST용 크리에이트 하고 지워야합니다.
    @Builder // lombok
    private SellerSaveRequest(String userId, String password, String name, String brandName, String bankName, String acountNumber, String city, String street, String zipCode, String companyCity, String companyStreet, String companyZipCode, String category) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.brandName = brandName;
        this.bankName = bankName;
        this.acountNumber = acountNumber;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.companyCity = companyCity;
        this.companyStreet = companyStreet;
        this.companyZipCode = companyZipCode;
        this.category = category;
    }
}
