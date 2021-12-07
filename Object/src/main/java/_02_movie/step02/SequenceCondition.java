package _02_movie.step02;

public class SequenceCondition implements DiscountCondition {
    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisFiedBy(Screening screening) {
        return screening.isSequence(sequence);
    }
}
