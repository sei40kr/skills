import java.util.Optional;
import java.util.Set;

public record Category(String value) {
  public static final Category FOOD = new Category("FOOD");
  public static final Category BOOKS = new Category("BOOKS");

  private static final Set<Category> KNOWN = Set.of(FOOD, BOOKS);

  public Category {
    if (value.isBlank()) {
      throw new IllegalArgumentException("Category must not be blank");
    }
  }

  public boolean isKnown() {
    return KNOWN.contains(this);
  }
}

interface CategoryRepository {
  Optional<CategoryDetails> find(Category category);
}
