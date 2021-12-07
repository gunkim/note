package _02_movie.step02;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class PeriodCondition implements DiscountCondition {
    private DayOfWeek dayOfWeek;
    private LocalDate startTime;
    private LocalDate endTime;

    public PeriodCondition(DayOfWeek dayOfWeek, LocalDate startTime, LocalDate endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean isSatisFiedBy(Screening screening) {
        return screening.getStartTime().getDayOfWeek().equals(this.dayOfWeek) &&
                this.startTime.compareTo(ChronoLocalDate.from(screening.getStartTime().toLocalTime())) <= 0 &&
                this.endTime.compareTo(ChronoLocalDate.from(screening.getStartTime().toLocalTime())) >= 0;
    }
}
