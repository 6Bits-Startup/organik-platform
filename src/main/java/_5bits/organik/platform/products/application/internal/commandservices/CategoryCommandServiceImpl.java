package _5bits.organik.platform.products.application.internal.commandservices;

import _5bits.organik.platform.products.application.commandservices.CategoryCommandService;
import _5bits.organik.platform.products.domain.model.aggregates.Category;
import _5bits.organik.platform.products.domain.model.commands.CreateCategoryCommand;
import _5bits.organik.platform.products.domain.model.commands.SeedCategoriesCommand;
import _5bits.organik.platform.products.domain.model.valueobjects.CategoryName;
import _5bits.organik.platform.products.domain.model.valueobjects.DefaultCategories;
import _5bits.organik.platform.products.domain.repositories.CategoryRepository;
import _5bits.organik.platform.shared.application.result.ApplicationError;
import _5bits.organik.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Application service that handles Category commands.
 */
@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;

    public CategoryCommandServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(CreateCategoryCommand command) {
        if (categoryRepository.existsByName(command.name()))
            return Result.failure(ApplicationError.conflict("Category", "Name '%s' already exists".formatted(command.name().value())));
        var category = new Category(command);
        try {
            category = categoryRepository.save(category);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-category", e.getMessage()));
        }
        return Result.success(category.getId());
    }

    @Override
    public void handle(SeedCategoriesCommand command) {
        Arrays.stream(DefaultCategories.values()).forEach(defaultCategory -> {
            var name = new CategoryName(defaultCategory.categoryName());
            if (!categoryRepository.existsByName(name)) {
                categoryRepository.save(new Category(new CreateCategoryCommand(name, defaultCategory.tone())));
            }
        });
    }
}
