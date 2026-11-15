package _5bits.organik.platform.products.domain.model.queries;

public record GetCategoryByIdQuery(Long id) {
    public GetCategoryByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must be greater than 0.");
    }
}
