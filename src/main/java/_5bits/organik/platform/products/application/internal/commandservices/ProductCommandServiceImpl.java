package _5bits.organik.platform.products.application.internal.commandservices;

import _5bits.organik.platform.products.application.commandservices.ProductCommandService;
import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.commands.CreateProductCommand;
import _5bits.organik.platform.products.domain.model.commands.UpdateProductCommand;
import _5bits.organik.platform.products.domain.repositories.CategoryRepository;
import _5bits.organik.platform.products.domain.repositories.ProductRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles Product commands.
 */
@Service
public class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateProductCommand command) {
        if (productRepository.existsBySku(command.sku()))
            return Result.failure(ApplicationError.conflict("Product", "SKU '%s' already exists".formatted(command.sku().value())));
        if (!categoryRepository.existsById(command.categoryId().value()))
            return Result.failure(ApplicationError.notFound("Category", command.categoryId().value().toString()));
        var product = new Product(command);
        try {
            product = productRepository.save(product);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-product", e.getMessage()));
        }
        return Result.success(product.getId());
    }

    @Override
    public Result<Long, ApplicationError> handle(UpdateProductCommand command) {
        var result = productRepository.findById(command.productId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("Product", command.productId().toString()));
        if (!categoryRepository.existsById(command.categoryId().value()))
            return Result.failure(ApplicationError.notFound("Category", command.categoryId().value().toString()));
        var productToUpdate = result.get();
        try {
            var updatedProduct = productRepository.save(productToUpdate.updateInformation(
                    command.name(), command.categoryId(), command.supplierId(), command.unit(), command.minimumStock()));
            return Result.success(updatedProduct.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("update-product", e.getMessage()));
        }
    }
}
