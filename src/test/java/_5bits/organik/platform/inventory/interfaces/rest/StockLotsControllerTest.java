package _5bits.organik.platform.inventory.interfaces.rest;

import _5bits.organik.platform.inventory.application.commandservices.StockLotCommandService;
import _5bits.organik.platform.inventory.application.queryservices.StockLotQueryService;
import _5bits.organik.platform.inventory.domain.model.aggregates.StockLot;
import _5bits.organik.platform.inventory.domain.model.commands.CreateStockLotCommand;
import _5bits.organik.platform.inventory.domain.model.queries.GetStockLotByIdQuery;
import _5bits.organik.platform.inventory.domain.model.valueobjects.*;
import _5bits.organik.platform.inventory.interfaces.rest.resources.CreateStockLotResource;
import _5bits.organik.platform.inventory.interfaces.rest.resources.StockLotResource;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockLotsControllerTest {

    @Mock
    private StockLotCommandService stockLotCommandService;

    @Mock
    private StockLotQueryService stockLotQueryService;

    private StockLotsController stockLotsController;

    @BeforeEach
    void setUp() {
        stockLotsController = new StockLotsController(stockLotCommandService, stockLotQueryService);
    }

    private final CreateStockLotResource resource = new CreateStockLotResource(
            "mm-vida-verde", "ORG-01", "LT-204", 30, 10, LocalDate.of(2026, 12, 20), "Cold room 1", "");

    @Test
    void createStockLotReturnsCreatedWhenSuccessful() {
        var stockLot = StockLot.reconstitute(1L, new MinimarketId("mm-vida-verde"), new ProductId("ORG-01"), new LotCode("LT-204"),
                new Quantity(30), new Quantity(10), new ExpirationDate(LocalDate.of(2026, 12, 20)),
                new LotLocation("Cold room 1"), new LotNotes(""));
        when(stockLotCommandService.handle(any(CreateStockLotCommand.class))).thenReturn(Result.success(1L));
        when(stockLotQueryService.handle(any(GetStockLotByIdQuery.class))).thenReturn(Optional.of(stockLot));

        var response = stockLotsController.createStockLot(resource);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("LT-204", ((StockLotResource) response.getBody()).lotCode());
    }

    @Test
    void createStockLotReturnsConflictWhenLotCodeExists() {
        when(stockLotCommandService.handle(any(CreateStockLotCommand.class)))
                .thenReturn(Result.failure(ApplicationError.conflict("StockLot", "Lot code already exists")));

        var response = stockLotsController.createStockLot(resource);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(409, ((ProblemDetail) response.getBody()).getStatus());
    }

    @Test
    void getStockLotsReturnsBadRequestWithoutMinimarketId() {
        var response = stockLotsController.getStockLots(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getStockLotByIdReturnsNotFoundWhenMissing() {
        when(stockLotQueryService.handle(any(GetStockLotByIdQuery.class))).thenReturn(Optional.empty());

        var response = stockLotsController.getStockLotById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
