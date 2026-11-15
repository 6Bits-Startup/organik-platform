package _5bits.organik.platform.requisition.domain.model.aggregates;

import _5bits.organik.platform.requisition.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SupplyRequestTest {

    private SupplyRequest pendingRequest() {
        return new SupplyRequest(new ProductId("ORG-01"), new SupplierId("sup-bioandes"), new MinimarketId("mm-vida-verde"),
                new Quantity(24), new RequestReason("Weekly demand"), LocalDate.of(2026, 11, 20), RequestPriority.HIGH);
    }

    @Test
    void newSupplyRequestIsPending() {
        assertThat(pendingRequest().getStatus()).isEqualTo(RequestStatus.PENDING);
    }

    @Test
    void acceptChangesTheStatus() {
        var request = pendingRequest();

        request.accept();

        assertThat(request.getStatus()).isEqualTo(RequestStatus.ACCEPTED);
    }

    @Test
    void answeredRequestCannotBeAnsweredAgain() {
        var request = pendingRequest();
        request.reject();

        assertThrows(IllegalStateException.class, request::accept);
    }
}
