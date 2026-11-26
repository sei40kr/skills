public sealed interface ContactPoints {
  record EmailOnly(EmailAddress email) implements ContactPoints {}

  record PhoneOnly(PhoneNumber phone) implements ContactPoints {}

  record Both(EmailAddress email, PhoneNumber phone) implements ContactPoints {}
}
