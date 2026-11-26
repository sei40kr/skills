public record DiscountRate(int percent) {
  public DiscountRate {
    if (percent < 0 || percent > 100) {
      throw new IllegalArgumentException("DiscountRate must be between 0 and 100 percent");
    }
  }
}
