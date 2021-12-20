package _01_ticket;

import _01_ticket.step03.Audience;
import _01_ticket.step03.Bag;
import _01_ticket.step03.Invitation;
import _01_ticket.step03.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("관람객 테스트")
public class AudienceTests {
    private static final Ticket MARVEL_TICKET = new Ticket(500l);

    @Test
    @DisplayName("관람객이 돈으로 티켓 구매")
    void Should_BuyTicket_When_Success() {
        Audience audience = new Audience(
                new Bag(1000l)
        );
        long purchasedTicketPrice = audience.buy(MARVEL_TICKET);
        assertThat(purchasedTicketPrice).isEqualTo(500l);
    }
    @Test
    @DisplayName("초대받은 관람객이 티켓 구매")
    void Should_InvitationBuyTicket_When_Success() {
        Audience audience = new Audience(
                new Bag(new Invitation(), 1000l)
        );
        long purchasedTicketPrice = audience.buy(MARVEL_TICKET);
        assertThat(purchasedTicketPrice).isEqualTo(0l);
    }
    @Test
    void test() {
        Audience audience = new Audience(
                new Bag(0l)
        );
    }
}
