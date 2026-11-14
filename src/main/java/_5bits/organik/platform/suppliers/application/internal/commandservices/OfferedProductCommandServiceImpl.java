package _5bits.organik.platform.suppliers.application.internal.commandservices;

import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.suppliers.application.commandservices.OfferedProductCommandService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateOfferedProductCommand;
import _5bits.organik.platform.suppliers.domain.repositories.OfferedProductRepository;
import org.springframework.stereotype.Service;

/**
 * Application service that handles OfferedProduct commands.
 */
@Service
public class OfferedProductCommandServiceImpl implements OfferedProductCommandService {
    private final OfferedProductRepository offeredProductRepository;

    public OfferedProductCommandServiceImpl(OfferedProductRepository offeredProductRepository) {
        this.offeredProductRepository = offeredProductRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateOfferedProductCommand command) {
        if (offeredProductRepository.existsBySupplierIdAndLotCode(command.supplierId(), command.lotCode()))
            return Result.failure(ApplicationError.conflict("OfferedProduct",
                    "Lot code '%s' is already offered by supplier '%s'".formatted(command.lotCode().value(), command.supplierId().value())));
        var offeredProduct = new OfferedProduct(command);
        try {
            offeredProduct = offeredProductRepository.save(offeredProduct);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-offered-product", e.getMessage()));
        }
        return Result.success(offeredProduct.getId());
    }
}
