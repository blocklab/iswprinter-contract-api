package co.hodler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"name=foo"}, webEnvironment = SpringBootTest
  .WebEnvironment.RANDOM_PORT)
public class PrintablesControllerTest {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  public void exampleTest() {
    Integer response = this.restTemplate.getForObject
      ("/printables/printableId/userId", Integer.class);
    assertThat(response).isEqualTo(1);
  }
}
