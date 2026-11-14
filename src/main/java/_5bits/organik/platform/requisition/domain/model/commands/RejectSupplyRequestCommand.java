package _5bits.organik.platform.requisition.domain.model.commands;

/**
 * Command to reject a pending supply request.
 *
 * @param supplyRequestId supply request identifier
 */
public record RejectSupplyRequestCommand(Long supplyRequestId) {
    public RejectSupplyRequestCommand {
        if (supplyRequestId == null || supplyRequestId <= 0) {
            throw new IllegalArgumentException("supplyRequestId cannot be null or less than 1");
        }
    }
}
