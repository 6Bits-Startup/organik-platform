package _5bits.organik.platform.products.application.internal.queryservices;

import _5bits.organik.platform.products.application.queryservices.ProductQueryService;
import _5bits.organik.platform.products.domain.model.aggregates.Product;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsByCategoryIdQuery;
import _5bits.organik.platform.products.domain.model.queries.GetAllProductsQuery;
import _5bits.organik.platform.products.domain.model.queries.GetProductByIdQuery;
import _5bits.organik.platform.products.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles Product queries.
 */
@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.id());
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return productRepository.findAll();
    }

    @Override
    public List<Product> handle(GetAllProductsByCategoryIdQuery query) {
        return productRepository.findAllByCategoryId(query.categoryId());
    }
}
