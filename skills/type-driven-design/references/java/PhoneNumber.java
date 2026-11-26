public record PhoneNumber(String value) {
  public PhoneNumber {
    if (value.length() < 10 || value.length() > 11) {
      throw new IllegalArgumentException("PhoneNumber must be 10 or 11 digits");
    }
    if (!value.chars().allMatch(c -> c >= '0' && c <= '9')) {
      throw new IllegalArgumentException("PhoneNumber allows only ASCII digits");
    }
    if (value.charAt(0) != '0') {
      throw new IllegalArgumentException("PhoneNumber must start with 0");
    }
  }
}
