package _5bits.organik.platform.inventory.domain.model.valueobjects;

/**
 * Type of an inventory movement.
 * <ul>
 *     <li>{@code ENTRY}: units received, increase the stock.</li>
 *     <li>{@code EXIT}: units sold or taken out, decrease the stock.</li>
 *     <li>{@code WASTE}: units discarded, decrease the stock.</li>
 *     <li>{@code ADJUSTMENT}: physical count, sets the stock to the counted quantity.</li>
 * </ul>
 */
public enum MovementType {
    ENTRY,
    EXIT,
    ADJUSTMENT,
    WASTE;

    public boolean requiresReason() {
        return this == ADJUSTMENT || this == WASTE;
    }

    public boolean decreasesStock() {
        return this == EXIT || this == WASTE;
    }
}
