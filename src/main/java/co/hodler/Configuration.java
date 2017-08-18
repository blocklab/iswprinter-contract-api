package co.hodler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configuration {
  @Value("${name}")
  public String name;
}
