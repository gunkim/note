package _02_movie.step01;

public class NoneDiscountPolicy extends DiscountPolicy {
    @Override
    public Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
