package toy.project.local_specialty.local_famous_goods.dto.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthoritiesEnum {

    NORMAL_SELLER("NOT_PAID_SELLER" , new AuthorityOptionEnum[]{ AuthorityOptionEnum.COMMISSION_OPTION , AuthorityOptionEnum.POWER_LINK}),
    NORMAL_BUYER("BRONZE" , new AuthorityOptionEnum[]{}),
    EMPTY_MEMBER("EMPTY" , new AuthorityOptionEnum[]{});
    private final String paid;
    private final AuthorityOptionEnum[] options;


    AuthoritiesEnum(String paid, AuthorityOptionEnum[] options) {
        this.paid = paid;
        this.options = options;
    }

    public static AuthoritiesEnum findAuthOptions(AuthorityOptionEnum option){
        return Arrays.stream(AuthoritiesEnum.values())
                .filter(paymentGroup -> hasAuthOption(paymentGroup , option))
                .findAny()
                .orElse(AuthoritiesEnum.EMPTY_MEMBER);
    }

    private static boolean hasAuthOption(AuthoritiesEnum Authorities , AuthorityOptionEnum option){
        return Arrays.stream(Authorities.options)
                .anyMatch(matchPayment ->  matchPayment == option);
    }
}
