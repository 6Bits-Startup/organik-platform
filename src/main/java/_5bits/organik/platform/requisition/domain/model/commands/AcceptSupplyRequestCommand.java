package _5bits.organik.platform.requisition.domain.model.commands;

/**
 * Command to accept a pending supply request.
 *
 * @param supplyRequestId supply request identifier
 */
public record AcceptSupplyRequestCommand(Long supplyRequestId) {
    public AcceptSupplyRequestCommand {
        if (supplyRequestId == null || supplyRequestId <= 0) {
            throw new IllegalArgumentException("supplyRequestId cannot be null or less than 1");
        }
    }
}
