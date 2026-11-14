package _5bits.organik.platform.requisition.application.internal.queryservices;

import _5bits.organik.platform.requisition.application.queryservices.SupplyRequestQueryService;
import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsByMinimarketIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetAllSupplyRequestsBySupplierIdQuery;
import _5bits.organik.platform.requisition.domain.model.queries.GetSupplyRequestByIdQuery;
import _5bits.organik.platform.requisition.domain.repositories.SupplyRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles SupplyRequest queries.
 */
@Service
public class SupplyRequestQueryServiceImpl implements SupplyRequestQueryService {
    private final SupplyRequestRepository supplyRequestRepository;

    public SupplyRequestQueryServiceImpl(SupplyRequestRepository supplyRequestRepository) {
        this.supplyRequestRepository = supplyRequestRepository;
    }

    @Override
    public Optional<SupplyRequest> handle(GetSupplyRequestByIdQuery query) {
        return supplyRequestRepository.findById(query.id());
    }

    @Override
    public List<SupplyRequest> handle(GetAllSupplyRequestsByMinimarketIdQuery query) {
        return supplyRequestRepository.findAllByMinimarketId(query.minimarketId());
    }

    @Override
    public List<SupplyRequest> handle(GetAllSupplyRequestsBySupplierIdQuery query) {
        return supplyRequestRepository.findAllBySupplierId(query.supplierId());
    }
}
