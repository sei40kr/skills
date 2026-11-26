public record Price(long amountExcludingTax, int taxRatePercent) {
  public Price {
    if (amountExcludingTax < 0) {
      throw new IllegalArgumentException("amountExcludingTax must not be negative");
    }
    if (taxRatePercent < 0) {
      throw new IllegalArgumentException("taxRatePercent must not be negative");
    }
  }

  public long amountIncludingTax() {
    return amountExcludingTax + amountExcludingTax * taxRatePercent / 100;
  }
}
