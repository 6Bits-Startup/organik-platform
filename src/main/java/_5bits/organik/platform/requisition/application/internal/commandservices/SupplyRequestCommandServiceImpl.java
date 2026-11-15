package _5bits.organik.platform.requisition.application.internal.commandservices;

import _5bits.organik.platform.requisition.application.commandservices.SupplyRequestCommandService;
import _5bits.organik.platform.requisition.domain.model.aggregates.SupplyRequest;
import _5bits.organik.platform.requisition.domain.model.commands.AcceptSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.model.commands.CreateSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.model.commands.RejectSupplyRequestCommand;
import _5bits.organik.platform.requisition.domain.repositories.SupplyRequestRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles SupplyRequest commands.
 */
@Service
public class SupplyRequestCommandServiceImpl implements SupplyRequestCommandService {
    private final SupplyRequestRepository supplyRequestRepository;

    public SupplyRequestCommandServiceImpl(SupplyRequestRepository supplyRequestRepository) {
        this.supplyRequestRepository = supplyRequestRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateSupplyRequestCommand command) {
        var supplyRequest = new SupplyRequest(command);
        try {
            supplyRequest = supplyRequestRepository.save(supplyRequest);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-supply-request", e.getMessage()));
        }
        return Result.success(supplyRequest.getId());
    }

    @Override
    public Result<Long, ApplicationError> handle(AcceptSupplyRequestCommand command) {
        return supplyRequestRepository.findById(command.supplyRequestId()).map(supplyRequest -> {
            if (!supplyRequest.isPending())
                return Result.<Long, ApplicationError>failure(ApplicationError.conflict("SupplyRequest", "Only pending supply requests can be accepted"));
            supplyRequest.accept();
            return Result.<Long, ApplicationError>success(supplyRequestRepository.save(supplyRequest).getId());
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("SupplyRequest", command.supplyRequestId().toString())));
    }

    @Override
    public Result<Long, ApplicationError> handle(RejectSupplyRequestCommand command) {
        return supplyRequestRepository.findById(command.supplyRequestId()).map(supplyRequest -> {
            if (!supplyRequest.isPending())
                return Result.<Long, ApplicationError>failure(ApplicationError.conflict("SupplyRequest", "Only pending supply requests can be rejected"));
            supplyRequest.reject();
            return Result.<Long, ApplicationError>success(supplyRequestRepository.save(supplyRequest).getId());
        }).orElseGet(() -> Result.failure(ApplicationError.notFound("SupplyRequest", command.supplyRequestId().toString())));
    }
}
