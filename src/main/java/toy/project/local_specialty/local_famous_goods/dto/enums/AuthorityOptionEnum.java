package toy.project.local_specialty.local_famous_goods.dto.enums;

import lombok.Getter;

@Getter
public enum AuthorityOptionEnum {

    POWER_LINK("파워링크"),
    COMMISSION_OPTION("수수료옵션");


    private final String optionName;

    AuthorityOptionEnum(String optionName) {
        this.optionName = optionName;
    }
}
