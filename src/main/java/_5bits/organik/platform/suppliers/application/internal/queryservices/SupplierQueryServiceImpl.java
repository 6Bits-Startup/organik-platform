package _5bits.organik.platform.suppliers.application.internal.queryservices;

import _5bits.organik.platform.suppliers.application.queryservices.SupplierQueryService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.Supplier;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllSuppliersQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetSupplierByIdQuery;
import _5bits.organik.platform.suppliers.domain.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles Supplier queries.
 */
@Service
public class SupplierQueryServiceImpl implements SupplierQueryService {
    private final SupplierRepository supplierRepository;

    public SupplierQueryServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Optional<Supplier> handle(GetSupplierByIdQuery query) {
        return supplierRepository.findById(query.id());
    }

    @Override
    public List<Supplier> handle(GetAllSuppliersQuery query) {
        return supplierRepository.findAll();
    }
}
