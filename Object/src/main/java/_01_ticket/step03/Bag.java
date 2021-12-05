package _01_ticket.step03;

public class Bag {
    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    public Bag(Long amount) {
        this(null, amount);
    }
    public Bag(Invitation invitation, Long amount) {
        this.amount = amount;
        this.invitation = invitation;
    }
    public Long hold(Ticket ticket) {
        if(hasInvitation()) {
            this.setTicket(ticket);
            return 0L;
        } else {
            this.setTicket(ticket);
            this.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    private boolean hasInvitation() {
        return this.invitation != null;
    }
    public boolean hasTicket() {
        return ticket != null;
    }
    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    private void minusAmount(Long amount) {
        this.amount -= amount;
    }
    public void plusAmount(Long amount) {
        this.amount += amount;
    }
}
