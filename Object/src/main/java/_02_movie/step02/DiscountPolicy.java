package _02_movie.step02;

public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening);
}
