import java.util.List;
import java.util.Set;

public record OrderLines(List<OrderLine> lines) {
  public OrderLines {
    lines = List.copyOf(lines);
    if (lines.isEmpty()) {
      throw new IllegalArgumentException("An order needs at least one line");
    }
  }
}

record ProductTags(Set<Tag> tags) {
  public ProductTags {
    tags = Set.copyOf(tags);
  }
}
