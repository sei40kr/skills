public record OrderLine(String productName, long unitPrice, int quantity) {
  public static OrderLine of(Product product, int quantity) {
    return new OrderLine(product.name(), product.price(), quantity);
  }
}

record Product(String name, long price) {}
