package toy.project.local_specialty.local_famous_goods.dto.enums;

import lombok.Getter;


import java.util.Arrays;

@Getter
public enum PaymentGroup {

    CASH("현금결제" , true , new PaymentOption[]{PaymentOption.BANK_TRANSFER , PaymentOption.DEPOSITLESS }),
    PG("결제대행사" , false , new PaymentOption[]{PaymentOption.MOBILE , PaymentOption.MOBILE , PaymentOption.SIMPLE_PAY , PaymentOption.CREDIT_CARD}),
    POINT_PAYMENT("포인트결제" , false , new PaymentOption[]{PaymentOption.COUPON , PaymentOption.POINT}),
    EMPTY("없음" , false , new PaymentOption[]{});
    private final String value;
    private final boolean discountRate;
    private final PaymentOption[] paymentOptions;

    PaymentGroup(String value, boolean discountRate, PaymentOption[] paymentOptions) {
        this.value = value;
        this.discountRate = discountRate;
        this.paymentOptions = paymentOptions;
    }

    public static PaymentGroup findPaymentGroup(PaymentOption paymentOption){
        return Arrays.stream(PaymentGroup.values())
                .filter(paymentGroup -> hasPaymentOption(paymentGroup , paymentOption))
                .findAny()
                .orElse(PaymentGroup.EMPTY);
    }

    private static boolean hasPaymentOption(PaymentGroup paymentGroup , PaymentOption paymentOption){
        return Arrays.stream(paymentGroup.paymentOptions)
                .anyMatch(matchPayment ->  matchPayment == paymentOption);
    }

}
