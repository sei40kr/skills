import java.time.Instant;

public record CouponPeriod(Instant start, Instant end) {
  public CouponPeriod {
    if (!end.isAfter(start)) {
      throw new IllegalArgumentException("end must be after start");
    }
  }

  public boolean contains(Instant at) {
    return !at.isBefore(start) && at.isBefore(end);
  }
}
