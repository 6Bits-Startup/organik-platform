package _5bits.organik.platform.suppliers.application.internal.queryservices;

import _5bits.organik.platform.suppliers.application.queryservices.OfferedProductQueryService;
import _5bits.organik.platform.suppliers.domain.model.aggregates.OfferedProduct;
import _5bits.organik.platform.suppliers.domain.model.queries.GetAllOfferedProductsBySupplierIdQuery;
import _5bits.organik.platform.suppliers.domain.model.queries.GetOfferedProductByIdQuery;
import _5bits.organik.platform.suppliers.domain.repositories.OfferedProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles OfferedProduct queries.
 */
@Service
public class OfferedProductQueryServiceImpl implements OfferedProductQueryService {
    private final OfferedProductRepository offeredProductRepository;

    public OfferedProductQueryServiceImpl(OfferedProductRepository offeredProductRepository) {
        this.offeredProductRepository = offeredProductRepository;
    }

    @Override
    public Optional<OfferedProduct> handle(GetOfferedProductByIdQuery query) {
        return offeredProductRepository.findById(query.id());
    }

    @Override
    public List<OfferedProduct> handle(GetAllOfferedProductsBySupplierIdQuery query) {
        return offeredProductRepository.findAllBySupplierId(query.supplierId());
    }
}
