package _5bits.organik.platform.suppliers.application.internal.commandservices;

import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import _5bits.organik.platform.suppliers.application.commandservices.SupplierCommandService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.commands.CreateSupplierCommand;
import _5bits.organik.platform.suppliers.domain.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

/**
 * Application service that handles Supplier commands.
 */
@Service
public class SupplierCommandServiceImpl implements SupplierCommandService {
    private final SupplierRepository supplierRepository;

    public SupplierCommandServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateSupplierCommand command) {
        if (supplierRepository.existsByEmail(command.email()))
            return Result.failure(ApplicationError.conflict("Supplier", "Email '%s' already exists".formatted(command.email().value())));
        var supplier = new Supplier(command);
        try {
            supplier = supplierRepository.save(supplier);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-supplier", e.getMessage()));
        }
        return Result.success(supplier.getId());
    }
}
