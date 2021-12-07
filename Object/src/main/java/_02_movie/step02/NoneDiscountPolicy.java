package _02_movie.step02;

public class NoneDiscountPolicy implements DiscountPolicy {
    @Override
    public Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
