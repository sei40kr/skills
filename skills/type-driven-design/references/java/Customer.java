import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public record Customer(CustomerId id, ContactPoints contact, @Nullable Instant lastLoginAt) {
  public Customer {
    Objects.requireNonNull(id, "id");
    Objects.requireNonNull(contact, "contact");
  }
}

interface CustomerRepository {
  Optional<Customer> findById(CustomerId id);
}
