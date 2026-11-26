public record Order(OrderId id, Address shippingAddress, Address billingAddress) {}

record Address(PostalCode postalCode, String line) {}
