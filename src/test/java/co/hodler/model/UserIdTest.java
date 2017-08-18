package co.hodler.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserIdTest {
  @Test
  public void is_42_characters_long() {
    String userIdValue = "0x38570825D2C35dB1B5fc603b1215Dcfe1607bFB4";
    UserId userId = new UserId(userIdValue);
    assertThat(userId.asString()).isEqualTo(userIdValue);
  }

  @Test
  public void cant_have_more_than_42_characters() {
    String userIdValue = "0x38570825D2C35dB1B5fc603b1215Dcfe1607bFB44";
    assertThatThrownBy(() -> new UserId(userIdValue))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void cant_have_less_than_42_characters() {
    String userIdValue = "0x38570825D2C35dB1B5fc603b1215Dcfe1607bFB";
    assertThatThrownBy(() -> new UserId(userIdValue))
      .isInstanceOf(IllegalArgumentException.class);
  }
}
