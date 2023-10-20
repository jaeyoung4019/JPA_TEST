package toy.project.local_specialty.local_famous_goods.dto.enums;

public enum PaymentOption {
    MOBILE("모바일"),
    CREDIT_CARD("신용카드"),
    SIMPLE_PAY("간편결제"),
    BANK_TRANSFER("계좌이체"),
    DEPOSITLESS("무통장입금"),
    POINT("포인트"),
    COUPON("쿠폰"),
    EMPTY("없음");

    private final String value;

    PaymentOption(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
