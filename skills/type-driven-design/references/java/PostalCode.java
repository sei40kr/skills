import java.util.regex.Pattern;

public record PostalCode(String value) {
  private static final Pattern CANONICAL = Pattern.compile("\\d{7}");
  private static final Pattern INPUT = Pattern.compile("(\\d{3})-?(\\d{4})");

  public PostalCode {
    if (!CANONICAL.matcher(value).matches()) {
      throw new IllegalArgumentException("PostalCode must be 7 digits");
    }
  }

  public static PostalCode parse(String input) {
    var m = INPUT.matcher(input.strip());
    if (!m.matches()) {
      throw new IllegalArgumentException("PostalCode input must be NNN-NNNN or NNNNNNN");
    }
    return new PostalCode(m.group(1) + m.group(2));
  }

  public String hyphenated() {
    return value.substring(0, 3) + "-" + value.substring(3);
  }
}
