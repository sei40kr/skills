import java.time.YearMonth;
import java.util.regex.Pattern;

public sealed interface PaymentMethod {
  record Card(CardNumber number, YearMonth expiry) implements PaymentMethod {}

  record BankTransfer(BankAccount payerAccount) implements PaymentMethod {}

  record CashOnDelivery() implements PaymentMethod {}
}

record CardNumber(String value) {
  private static final Pattern FORMAT = Pattern.compile("\\d{14,19}");

  public CardNumber {
    if (!FORMAT.matcher(value).matches()) {
      throw new IllegalArgumentException("CardNumber must be 14 to 19 digits");
    }
  }

  public String last4() {
    return value.substring(value.length() - 4);
  }

  @Override
  public String toString() {
    return "CardNumber[****" + last4() + "]";
  }
}

final class CashOnDeliveryFees {
  static long codFee(PaymentMethod method) {
    return switch (method) {
      case PaymentMethod.Card card -> 0;
      case PaymentMethod.BankTransfer transfer -> 0;
      case PaymentMethod.CashOnDelivery cod -> 330;
    };
  }

  private CashOnDeliveryFees() {}
}
