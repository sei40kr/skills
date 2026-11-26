import java.time.Instant;

public sealed interface Shipment {
  OrderId orderId();

  record Preparing(OrderId orderId) implements Shipment {}

  record Shipped(OrderId orderId, TrackingNumber trackingNumber, Instant shippedAt)
      implements Shipment {}

  record Delivered(
      OrderId orderId, TrackingNumber trackingNumber, Instant shippedAt, Instant deliveredAt)
      implements Shipment {
    public Delivered {
      if (deliveredAt.isBefore(shippedAt)) {
        throw new IllegalArgumentException("deliveredAt must not be before shippedAt");
      }
    }
  }
}

final class ShipmentDescriptions {
  static String describe(Shipment shipment) {
    return switch (shipment) {
      case Shipment.Preparing p -> "Preparing";
      case Shipment.Shipped s -> "Shipped: " + s.trackingNumber();
      case Shipment.Delivered d -> "Delivered at " + d.deliveredAt();
    };
  }

  private ShipmentDescriptions() {}
}
