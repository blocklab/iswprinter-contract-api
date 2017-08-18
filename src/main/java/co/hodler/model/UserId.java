package co.hodler.model;

public class UserId {

  private final String userId;

  public UserId(String userId) {
    final int userIdCorrectLength = 42;
    if (userId.length() != userIdCorrectLength) {
      throw new IllegalArgumentException();
    }
    this.userId = userId;
  }

  public String asString() {
    return userId;
  }
}
