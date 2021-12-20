package _02_movie;

import _02_movie.step02.AmountDiscountPolicy;
import _02_movie.step02.Money;
import _02_movie.step02.Movie;
import _02_movie.step02.Screening;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class MovieTests {
    @Test
    void test() {
        Movie avatar = new Movie("아바타",
                Duration.ofMillis(120),
                Money.wons(10000),
                new AmountDiscountPolicy(Money.wons(800)));
        Screening avatarScreen = new Screening(avatar, 10, LocalDateTime.now());

        System.out.println(avatarScreen.getStartTime());
    }
}
