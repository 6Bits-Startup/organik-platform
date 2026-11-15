package _5bits.organik.platform.products.domain.model.valueobjects;

/**
 * Product categories every minimarket starts with.
 * The name of each category is the identifier the web application uses to translate it.
 */
public enum DefaultCategories {
    FRUITS_VEGETABLES("fruits-vegetables", CategoryTone.GREEN),
    DAIRY("dairy", CategoryTone.BLUE),
    GRAINS("grains", CategoryTone.ORANGE),
    BEVERAGES("beverages", CategoryTone.YELLOW),
    BAKERY("bakery", CategoryTone.NEUTRAL),
    FROZEN("frozen", CategoryTone.NEUTRAL);

    private final String categoryName;
    private final CategoryTone tone;

    DefaultCategories(String categoryName, CategoryTone tone) {
        this.categoryName = categoryName;
        this.tone = tone;
    }

    public String categoryName() {
        return categoryName;
    }

    public CategoryTone tone() {
        return tone;
    }
}
