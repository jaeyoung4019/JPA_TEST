package toy.project.local_specialty.local_famous_goods.dto.member.modify_password;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class MemberPasswordModifyRequest {
    @Length(min = 8 , max = 20)
    private String userId;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[A-Za-z\\d]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[a-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$"
            , message = "특수문자, 소문자 , 대문자 , 숫자중 3가지를 포함, 9글자 이상 20글자 이하로 비밀번호를 작성해주세요")
    private String oldPassword;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[A-Za-z\\d]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[a-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z\\d!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$|^(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$%’()*+,./:;?@\\^_`{|}~-])[A-Za-z!#$%’()*+,./:;?@\\^_`{|}~-]{9,20}$"
            , message = "특수문자, 소문자 , 대문자 , 숫자중 3가지를 포함, 9글자 이상 20글자 이하로 비밀번호를 작성해주세요")
    private String password;
}
